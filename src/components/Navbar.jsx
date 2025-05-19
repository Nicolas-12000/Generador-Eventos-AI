export default function Navbar() {
  return (
    <nav className="bg-white shadow-lg p-4">
  <div className="container mx-auto flex justify-between items-center">
    <h1 className="text-xl font-bold text-blue-600">EventGen AI</h1>
    <button 
      className="bg-event-primary text-white px-4 py-2 rounded hover:bg-event-secondary transition-colors"
      onClick={() => console.log('Cerrar sesión')}
    >
      Cerrar Sesión
    </button>
  </div>
</nav>
  );
}