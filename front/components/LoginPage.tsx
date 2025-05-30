import React, { useState, useCallback, FormEvent } from 'react';

// Tipos (simulando las importaciones originales)
interface User {
  email: string;
  name?: string;
}

// Simulación de Button component (manteniendo la estructura original)
const Button: React.FC<{
  type?: 'button' | 'submit';
  variant?: 'primary' | 'secondary';
  size?: 'sm' | 'md' | 'lg';
  className?: string;
  children: React.ReactNode;
  onClick?: () => void;
  disabled?: boolean;
}> = ({ 
  type = 'button', 
  variant = 'primary', 
  size = 'md', 
  className = '', 
  children, 
  onClick,
  disabled = false
}) => {
  const baseClasses = 'font-medium rounded-lg transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed';
  const variantClasses = {
    primary: 'bg-gray-900 hover:bg-gray-800 text-white focus:ring-gray-500',
    secondary: 'bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 focus:ring-gray-500'
  };
  const sizeClasses = {
    sm: 'px-3 py-2 text-sm',
    md: 'px-4 py-2 text-base',
    lg: 'px-6 py-3 text-lg'
  };

  return (
    <button
      type={type}
      className={`${baseClasses} ${variantClasses[variant]} ${sizeClasses[size]} ${className}`}
      onClick={onClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
};

// Simulación de Input component (manteniendo la estructura original)
const Input: React.FC<{
  label: string;
  id: string;
  name: string;
  type: string;
  autoComplete?: string;
  required?: boolean;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onBlur?: () => void;
  error?: string;
  placeholder?: string;
  minLength?: number;
  maxLength?: number;
}> = ({ label, id, error, ...props }) => {
  return (
    <div>
      <label htmlFor={id} className="block text-sm font-medium text-gray-700 mb-1">
        {label}
      </label>
      <input
        id={id}
        className={`block w-full px-3 py-2 border ${
          error 
            ? 'border-red-300 focus:border-red-500 focus:ring-red-500' 
            : 'border-gray-300 focus:border-gray-500 focus:ring-gray-500'
        } rounded-md shadow-sm focus:outline-none focus:ring-1 sm:text-sm`}
        {...props}
      />
      {error && (
        <p className="mt-1 text-sm text-red-600">{error}</p>
      )}
    </div>
  );
};

// Componente para mostrar fortaleza de contraseña
const PasswordStrengthIndicator: React.FC<{ password: string }> = ({ password }) => {
  const getPasswordStrength = () => {
    let strength = 0;
    if (password.length >= 8) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/[a-z]/.test(password)) strength++;
    if (/\d/.test(password)) strength++;
    if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) strength++;
    return strength;
  };

  const strength = getPasswordStrength();
  
  if (!password) return null;

  const getStrengthLabel = () => {
    if (strength < 2) return 'Muy débil';
    if (strength < 3) return 'Débil';
    if (strength < 4) return 'Moderada';
    if (strength < 5) return 'Fuerte';
    return 'Muy fuerte';
  };

  const getStrengthColor = () => {
    if (strength < 2) return 'bg-red-500';
    if (strength < 3) return 'bg-orange-500';
    if (strength < 4) return 'bg-yellow-500';
    if (strength < 5) return 'bg-blue-500';
    return 'bg-green-500';
  };

  return (
    <div className="mt-2">
      <div className="flex justify-between text-xs text-gray-600 mb-1">
        <span>Seguridad de la contraseña</span>
        <span>{getStrengthLabel()}</span>
      </div>
      <div className="w-full bg-gray-200 rounded-full h-1.5">
        <div 
          className={`h-1.5 rounded-full transition-all duration-300 ${getStrengthColor()}`}
          style={{ width: `${(strength / 5) * 100}%` }}
        />
      </div>
    </div>
  );
};

// Login Page (manteniendo estructura original)
interface LoginPageProps {
  onLogin: (user: User) => void;
}

const LoginPage: React.FC<LoginPageProps> = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [emailError, setEmailError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const validateEmail = useCallback(() => {
    if (!email) {
      setEmailError('El correo electrónico es obligatorio.');
      return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setEmailError('Por favor, introduce un correo electrónico válido.');
      return false;
    }
    setEmailError('');
    return true;
  }, [email]);

  const validatePassword = useCallback(() => {
    if (!password) {
      setPasswordError('La contraseña es obligatoria.');
      return false;
    }
    if (password.length < 8 || password.length > 30) {
      setPasswordError('La contraseña debe tener entre 8 y 30 caracteres.');
      return false;
    }
    const strongPasswordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,30}$/;
    if (!strongPasswordRegex.test(password)) {
      setPasswordError('La contraseña debe contener al menos una mayúscula, una minúscula, un número y un símbolo.');
      return false;
    }
    setPasswordError('');
    return true;
  }, [password]);

  const handleSubmit = useCallback((event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();

    if (isEmailValid && isPasswordValid) {
      console.log('Simulando inicio de sesión con:', { email, password });
      onLogin({ email });
    } else {
      if (!isEmailValid) {
        document.getElementById('email')?.focus();
      } else if (!isPasswordValid) {
        document.getElementById('password')?.focus();
      }
    }
  }, [email, password, onLogin, validateEmail, validatePassword]);

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow-lg sm:rounded-lg sm:px-10 border border-gray-200">
          <div className="sm:mx-auto sm:w-full sm:max-w-md mb-6">
            <h2 className="text-center text-2xl font-bold text-gray-900">
              Iniciar Sesión
            </h2>
            <p className="mt-2 text-center text-sm text-gray-600">
              Accede a tu panel de control
            </p>
          </div>

          <div className="space-y-6">
            <Input
              label="Correo Electrónico"
              id="email"
              name="email"
              type="email"
              autoComplete="email"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              onBlur={validateEmail}
              error={emailError}
              placeholder="correo@empresa.com"
            />

            <div className="relative">
              <Input
                label="Contraseña"
                id="password"
                name="password"
                type={showPassword ? "text" : "password"}
                autoComplete="current-password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onBlur={validatePassword}
                error={passwordError}
                placeholder="••••••••"
                minLength={8}
                maxLength={30}
              />
              <button
                type="button"
                className="absolute inset-y-0 right-0 top-6 pr-3 flex items-center text-gray-400 hover:text-gray-600"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? (
                  <svg className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L6.05 6.05M9.878 9.878a3 3 0 105.656 5.656m0 0L19.95 19.95M15.536 15.536a9.97 9.97 0 001.563-3.029" />
                  </svg>
                ) : (
                  <svg className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                  </svg>
                )}
              </button>
            </div>

            <div>
              <Button 
                type="button" 
                variant="primary" 
                size="lg" 
                className="w-full"
                onClick={() => handleSubmit({preventDefault: () => {}} as FormEvent<HTMLFormElement>)}
              >
                Iniciar Sesión
              </Button>
            </div>
          </div>

          <p className="mt-6 text-center text-xs text-gray-500">
            Sistema de autenticación seguro con validación del lado del cliente.
          </p>
        </div>
      </div>
    </div>
  );
};

// Register Page
const RegisterPage: React.FC<LoginPageProps> = ({ onLogin }) => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [nameError, setNameError] = useState('');
  const [emailError, setEmailError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [confirmPasswordError, setConfirmPasswordError] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const validateName = useCallback(() => {
    if (!name.trim()) {
      setNameError('El nombre completo es obligatorio.');
      return false;
    }
    if (name.trim().length < 2) {
      setNameError('El nombre debe tener al menos 2 caracteres.');
      return false;
    }
    setNameError('');
    return true;
  }, [name]);

  const validateEmail = useCallback(() => {
    if (!email) {
      setEmailError('El correo electrónico es obligatorio.');
      return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setEmailError('Por favor, introduce un correo electrónico válido.');
      return false;
    }
    setEmailError('');
    return true;
  }, [email]);

  const validatePassword = useCallback(() => {
    if (!password) {
      setPasswordError('La contraseña es obligatoria.');
      return false;
    }
    if (password.length < 8 || password.length > 30) {
      setPasswordError('La contraseña debe tener entre 8 y 30 caracteres.');
      return false;
    }
    const strongPasswordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,30}$/;
    if (!strongPasswordRegex.test(password)) {
      setPasswordError('La contraseña debe contener al menos una mayúscula, una minúscula, un número y un símbolo.');
      return false;
    }
    setPasswordError('');
    return true;
  }, [password]);

  const validateConfirmPassword = useCallback(() => {
    if (!confirmPassword) {
      setConfirmPasswordError('Confirma tu contraseña.');
      return false;
    }
    if (password !== confirmPassword) {
      setConfirmPasswordError('Las contraseñas no coinciden.');
      return false;
    }
    setConfirmPasswordError('');
    return true;
  }, [password, confirmPassword]);

  const handleSubmit = useCallback((event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const isNameValid = validateName();
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();
    const isConfirmPasswordValid = validateConfirmPassword();

    if (isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid) {
      console.log('Simulando registro con:', { name, email });
      onLogin({ email, name });
    }
  }, [name, email, password, confirmPassword, onLogin, validateName, validateEmail, validatePassword, validateConfirmPassword]);

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow-lg sm:rounded-lg sm:px-10 border border-gray-200">
          <div className="sm:mx-auto sm:w-full sm:max-w-md mb-6">
            <h2 className="text-center text-2xl font-bold text-gray-900">
              Crear Cuenta
            </h2>
            <p className="mt-2 text-center text-sm text-gray-600">
              Regístrate para acceder al sistema
            </p>
          </div>

          <div className="space-y-6">
            <Input
              label="Nombre Completo"
              id="name"
              name="name"
              type="text"
              autoComplete="name"
              required
              value={name}
              onChange={(e) => setName(e.target.value)}
              onBlur={validateName}
              error={nameError}
              placeholder="Juan Pérez"
            />

            <Input
              label="Correo Electrónico"
              id="email"
              name="email"
              type="email"
              autoComplete="email"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              onBlur={validateEmail}
              error={emailError}
              placeholder="correo@empresa.com"
            />

            <div className="relative">
              <Input
                label="Contraseña"
                id="password"
                name="password"
                type={showPassword ? "text" : "password"}
                autoComplete="new-password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onBlur={validatePassword}
                error={passwordError}
                placeholder="••••••••"
                minLength={8}
                maxLength={30}
              />
              <button
                type="button"
                className="absolute inset-y-0 right-0 top-6 pr-3 flex items-center text-gray-400 hover:text-gray-600"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? (
                  <svg className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L6.05 6.05M9.878 9.878a3 3 0 105.656 5.656m0 0L19.95 19.95M15.536 15.536a9.97 9.97 0 001.563-3.029" />
                  </svg>
                ) : (
                  <svg className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                  </svg>
                )}
              </button>
              <PasswordStrengthIndicator password={password} />
            </div>

            <Input
              label="Confirmar Contraseña"
              id="confirmPassword"
              name="confirmPassword"
              type="password"
              autoComplete="new-password"
              required
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              onBlur={validateConfirmPassword}
              error={confirmPasswordError}
              placeholder="••••••••"
            />

            <div>
              <Button 
                type="button" 
                variant="primary" 
                size="lg" 
                className="w-full"
                onClick={() => handleSubmit({preventDefault: () => {}} as FormEvent<HTMLFormElement>)}
              >
                Crear Cuenta
              </Button>
            </div>
          </div>

          <p className="mt-6 text-center text-xs text-gray-500">
            Al registrarte, aceptas nuestros términos de servicio y política de privacidad.
          </p>
        </div>
      </div>
    </div>
  );
};

// Dashboard Principal
const Dashboard: React.FC<{ user: User; onLogout: () => void }> = ({ user, onLogout }) => {
  const [activeTab, setActiveTab] = useState('overview');

  const stats = [
    { name: 'Total de Eventos', value: '24', change: '+12%', changeType: 'positive' },
    { name: 'Usuarios Activos', value: '1,847', change: '+3%', changeType: 'positive' },
    { name: 'Ingresos Mensuales', value: '$12,450', change: '+8%', changeType: 'positive' },
    { name: 'Tasa de Conversión', value: '3.24%', change: '-2%', changeType: 'negative' },
  ];

  const recentEvents = [
    { id: 1, name: 'Conferencia de Tecnología 2024', date: '2024-06-15', status: 'Activo', attendees: 150 },
    { id: 2, name: 'Workshop de Marketing Digital', date: '2024-06-10', status: 'Completado', attendees: 75 },
    { id: 3, name: 'Seminario de Finanzas', date: '2024-06-08', status: 'Completado', attendees: 120 },
    { id: 4, name: 'Curso de Desarrollo Web', date: '2024-06-20', status: 'Programado', attendees: 200 },
  ];

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-white shadow-sm border-b border-gray-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-4">
            <div className="flex items-center">
              <h1 className="text-2xl font-bold text-gray-900">Panel de Control</h1>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-sm text-gray-700">Bienvenido, {user.name || user.email}</span>
              <Button variant="secondary" size="sm" onClick={onLogout}>
                Cerrar Sesión
              </Button>
            </div>
          </div>
        </div>
      </header>

      {/* Navigation */}
      <nav className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex space-x-8">
            {[
              { id: 'overview', name: 'Resumen' },
              { id: 'events', name: 'Eventos' },
              { id: 'users', name: 'Usuarios' },
              { id: 'analytics', name: 'Analíticas' },
              { id: 'settings', name: 'Configuración' },
            ].map((tab) => (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`py-4 px-1 border-b-2 font-medium text-sm ${
                  activeTab === tab.id
                    ? 'border-gray-900 text-gray-900'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                }`}
              >
                {tab.name}
              </button>
            ))}
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <div className="px-4 py-6 sm:px-0">
          {activeTab === 'overview' && (
            <div className="space-y-6">
              {/* Stats */}
              <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
                {stats.map((stat) => (
                  <div key={stat.name} className="bg-white overflow-hidden shadow rounded-lg">
                    <div className="p-5">
                      <div className="flex items-center">
                        <div className="flex-1">
                          <p className="text-sm font-medium text-gray-500 truncate">{stat.name}</p>
                          <p className="text-2xl font-semibold text-gray-900">{stat.value}</p>
                        </div>
                        <div className={`text-xs font-medium ${
                          stat.changeType === 'positive' ? 'text-green-600' : 'text-red-600'
                        }`}>
                          {stat.change}
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              {/* Recent Events Table */}
              <div className="bg-white shadow overflow-hidden sm:rounded-md">
                <div className="px-4 py-5 sm:px-6">
                  <h3 className="text-lg leading-6 font-medium text-gray-900">Eventos Recientes</h3>
                  <p className="mt-1 max-w-2xl text-sm text-gray-500">Lista de eventos más recientes</p>
                </div>
                <div className="border-t border-gray-200">
                  <div className="overflow-x-auto">
                    <table className="min-w-full divide-y divide-gray-200">
                      <thead className="bg-gray-50">
                        <tr>
                          <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Evento
                          </th>
                          <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Fecha
                          </th>
                          <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Estado
                          </th>
                          <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Asistentes
                          </th>
                        </tr>
                      </thead>
                      <tbody className="bg-white divide-y divide-gray-200">
                        {recentEvents.map((event) => (
                          <tr key={event.id}>
                            <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                              {event.name}
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                              {event.date}
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap">
                              <span className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${
                                event.status === 'Activo' ? 'bg-green-100 text-green-800' :
                                event.status === 'Completado' ? 'bg-gray-100 text-gray-800' :
                                'bg-blue-100 text-blue-800'
                              }`}>
                                {event.status}
                              </span>
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                              {event.attendees}
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          )}

          {activeTab !== 'overview' && (
            <div className="bg-white shadow sm:rounded-lg">
              <div className="px-4 py-5 sm:p-6">
                <h3 className="text-lg leading-6 font-medium text-gray-900 mb-4">
                  {activeTab === 'events' && 'Gestión de Eventos'}
                  {activeTab === 'users' && 'Gestión de Usuarios'}
                  {activeTab === 'analytics' && 'Analíticas y Reportes'}
                  {activeTab === 'settings' && 'Configuración del Sistema'}
                </h3>
                <p className="text-sm text-gray-500">
                  Contenido de la sección {activeTab} - Esta sección está en desarrollo.
                </p>
              </div>
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

// Componente Principal
const AuthSystem: React.FC = () => {
  const [currentPage, setCurrentPage] = useState<'login' | 'register'>('login');
  const [user, setUser] = useState<User | null>(null);

  const handleLogin = (userData: User) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
    setCurrentPage('login');
  };

  if (user) {
    return <Dashboard user={user} onLogout={handleLogout} />;
  }

  return (
    <div>
      {currentPage === 'login' ? (
        <LoginPage onLogin={handleLogin} />
      ) : (
        <RegisterPage onLogin={handleLogin} />
      )}
      
      <div className="fixed bottom-4 left-1/2 transform -translate-x-1/2">
        <Button
          onClick={() => setCurrentPage(currentPage === 'login' ? 'register' : 'login')}
          variant="secondary"
          size="sm"
        >
          {currentPage === 'login' ? '¿No tienes cuenta? Regístrate' : '¿Ya tienes cuenta? Inicia sesión'}
        </Button>
      </div>
    </div>
  );
};

export default AuthSystem;