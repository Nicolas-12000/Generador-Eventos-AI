export default function ChatBubble({ text, isUser, isLoading = false }) {
  return (
    <div className={`flex ${isUser ? 'justify-end' : 'justify-start'} mb-2`}>
      <div
        className={`max-w-xs md:max-w-md rounded-lg p-3 ${
          isUser 
            ? 'bg-event-primary text-white' 
            : isLoading 
              ? 'bg-gray-200 text-gray-800 animate-pulse'
              : 'bg-white border border-gray-200 text-gray-800'
        } shadow-sm`}
      >
        {isLoading ? (
          <div className="flex space-x-2">
            <div className="w-2 h-2 rounded-full bg-gray-400 animate-bounce"></div>
            <div className="w-2 h-2 rounded-full bg-gray-400 animate-bounce delay-100"></div>
            <div className="w-2 h-2 rounded-full bg-gray-400 animate-bounce delay-200"></div>
          </div>
        ) : (
          text
        )}
      </div>
    </div>
  );
}