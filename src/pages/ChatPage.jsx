import Navbar from '../components/Navbar';
import ChatBubble from '../components/ChatBubble';
import { sendChatMessage } from '../services/api';

export default function ChatPage() {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');

 const handleSend = async () => {
  if (input.trim()) {
    const userMessage = { text: input, isUser: true };
    setMessages([...messages, userMessage]);
    
    try {
      const response = await sendChatMessage(input);
      setMessages(prev => [...prev, { text: response.data, isUser: false }]);
    } catch (error) {
      setMessages(prev => [...prev, { text: "Error al conectar con el servidor", isUser: false }]);
    }
    
    setInput('');
  }
};

  return (
    <div className="flex flex-col h-screen">
      <Navbar />
      <div className="flex-1 p-4 overflow-y-auto">
        {messages.map((msg, i) => (
          <ChatBubble key={i} text={msg.text} isUser={msg.isUser} />
        ))}
      </div>
      <div className="p-4 border-t">
        <div className="flex gap-2">
          <input
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            className="flex-1 border p-2 rounded"
            placeholder="Describe tu evento..."
          />
          <button
            onClick={handleSend}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
          >
            Enviar
          </button>
        </div>
      </div>
    </div>
  );
}