
import React, { useState, useCallback, FormEvent } from 'react';
import { User } from '../types';
import Button from './ui/Button';
import Input from './ui/Input';

interface LoginPageProps {
  onLogin: (user: User) => void;
}

const LoginPage: React.FC<LoginPageProps> = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [emailError, setEmailError] = useState('');
  const [passwordError, setPasswordError] = useState('');

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
    // Example: At least one uppercase, one symbol, one number
    const strongPasswordRegex = /^(?=.*[A-Z])(?=.*\W)(?=.*\d).{8,30}$/;
     if (!strongPasswordRegex.test(password)) {
       setPasswordError('La contraseña debe contener al menos una mayúscula, un símbolo y un número.');
       return false;
     }
    setPasswordError('');
    return true;
  }, [password]);

  const handleSubmit = useCallback((event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // HTML5 validation is implicitly handled by browser based on input types and attributes
    // Custom JS validation:
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();

    if (isEmailValid && isPasswordValid) {
      // Simulate API call / successful login
      console.log('Simulando inicio de sesión con:', { email, password });
      onLogin({ email });
    } else {
      // Focus first field with error
      if (!isEmailValid) {
        document.getElementById('email')?.focus();
      } else if (!isPasswordValid) {
        document.getElementById('password')?.focus();
      }
    }
  }, [email, password, onLogin, validateEmail, validatePassword]);

  return (
    <div className="flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8 bg-white p-8 sm:p-10 rounded-xl shadow-2xl">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-slate-900">
            Iniciar Sesión
          </h2>
          <p className="mt-2 text-center text-sm text-slate-600">
            Accede a tu cuenta para generar eventos increíbles.
          </p>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit} noValidate>
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
            placeholder="tu@ejemplo.com"
          />
          <Input
            label="Contraseña"
            id="password"
            name="password"
            type="password"
            autoComplete="current-password"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            onBlur={validatePassword}
            error={passwordError}
            placeholder="••••••••"
            // HTML5 validation attributes
            minLength={8}
            maxLength={30}
            // pattern="^(?=.*[A-Z])(?=.*\W)(?=.*\d).{8,30}$" // For browsers supporting pattern title
            // title="Debe contener 8-30 caracteres, incl. mayúscula, símbolo y número."
          />
          
          <div>
            <Button type="submit" variant="primary" size="lg" className="w-full">
              Ingresar
            </Button>
          </div>
        </form>
         <p className="mt-4 text-center text-xs text-slate-500">
          Este es un formulario de demostración. La validación del lado del cliente mejora la UX, pero la validación del lado del servidor es crucial para la seguridad.
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
