export default function ChatBubble({ text, isUser }) {
  return (
    <div className={`flex ${isUser ? 'justify-end' : 'justify-start'} mb-4`}>
      <div
        className={`max-w-xs md:max-w-md rounded-lg p-3 ${
          isUser ? 'bg-blue-500 text-white' : 'bg-gray-200'
        }`}
      >
        {text}
      </div>
    </div>
  );
}