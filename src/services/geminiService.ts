import { GoogleGenAI, GenerateContentResponse, GroundingChunk } from "@google/genai";
// import { GEMINI_API_KEY, GEMINI_TEXT_MODEL } from '../constants'; // API_KEY se toma de process.env directamente
import { GEMINI_TEXT_MODEL } from '../constants';


// API_KEY se obtiene directamente de process.env.API_KEY al inicializar GoogleGenAI.
// No es necesario verificarlo aquí explícitamente si se sigue la directriz de que está preconfigurado.
// Si no estuviera configurado, la inicialización de GoogleGenAI fallaría o las llamadas a la API fallarían.
// const apiKey = process.env.API_KEY;
// if (!apiKey) {
//   console.warn(
//     `API_KEY no encontrada. Por favor, configura la variable de entorno API_KEY.`
//   );
// }

const ai = new GoogleGenAI({ apiKey: process.env.API_KEY! }); // Se asume que API_KEY está en process.env

export interface GeminiResponse {
  text: string;
  sourceUrls?: { title: string; uri: string }[];
}

// const parseAndCleanJson = (jsonString: string): any => { // No se usa actualmente
//     let cleanedJsonString = jsonString.trim();
//     const fenceRegex = /^```(\w*)?\s*\n?(.*?)\n?\s*```$/s;
//     const match = cleanedJsonString.match(fenceRegex);
//     if (match && match[2]) {
//       cleanedJsonString = match[2].trim();
//     }
//     try {
//       return JSON.parse(cleanedJsonString);
//     } catch (e) {
//       console.error("Error al parsear JSON de Gemini:", e, "String original:", jsonString);
//       throw new Error("Respuesta JSON inválida del modelo IA.");
//     }
// };


export const generateEventDescription = async (eventName: string, eventType?: string): Promise<GeminiResponse> => {
  if (!process.env.API_KEY) { // Chequeo adicional por si acaso, aunque las directrices sugieren que siempre estará.
    console.warn("API_KEY de Gemini no configurada. Se usará descripción de ejemplo.");
    return { text: `Descripción de ejemplo para ${eventName}: Un evento fantástico que no te puedes perder.` };
  }
  try {
    const prompt = `Eres un asistente creativo para la planificación de eventos. 
    Genera una descripción breve y atractiva (máximo 2 frases, ~30-50 palabras) para un evento llamado "${eventName}".
    ${eventType ? `El tipo de evento es: "${eventType}".` : ''}
    La descripción debe ser entusiasta e invitar a la gente a asistir.
    No incluyas saludos ni despedidas, solo la descripción.`;

    const response: GenerateContentResponse = await ai.models.generateContent({
        model: GEMINI_TEXT_MODEL,
        contents: prompt,
        config: {
            temperature: 0.7,
            maxOutputTokens: 100,
        }
    });
    
    return { text: response.text.trim() };

  } catch (error)
 {
    console.error('Error al generar descripción con Gemini API:', error);
    // No exponer (error as Error).message directamente al usuario si esto fuera producción sensible
    // pero para depuración está bien.
    return { text: `Hubo un problema al generar la descripción con IA. Por favor, inténtalo de nuevo o escribe una manualmente.` };
  }
};

// Ejemplo de uso de grounding (no implementado en la UI actual pero como referencia)
export const getRecentEventInfo = async (query: string): Promise<GeminiResponse> => {
  if (!process.env.API_KEY) {
     console.warn("API_KEY de Gemini no configurada.");
    return { text: "API Key no configurada." };
  }
  try {
    const response: GenerateContentResponse = await ai.models.generateContent({
      model: GEMINI_TEXT_MODEL,
      contents: query,
      config: {
        tools: [{ googleSearch: {} }],
      },
    });

    const groundingMetadata = response.candidates?.[0]?.groundingMetadata;
    let sourceUrls: { title: string; uri: string }[] = [];
    if (groundingMetadata?.groundingChunks) {
      sourceUrls = (groundingMetadata.groundingChunks as GroundingChunk[])
        .filter(chunk => chunk.web && chunk.web.uri && chunk.web.title)
        .map(chunk => ({ title: chunk.web!.title, uri: chunk.web!.uri }));
    }
    
    return { text: response.text, sourceUrls };

  } catch (error) {
    console.error('Error con Gemini API (grounding):', error);
    return { text: `Hubo un problema al obtener información con IA.` };
  }
};