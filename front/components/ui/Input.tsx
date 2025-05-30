
import React, { useState } from 'react';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  error?: string;
  containerClassName?: string;
}

const Input: React.FC<InputProps> = ({ label, id, error, containerClassName, type = "text", ...props }) => {
  const [isFocused, setIsFocused] = useState(false);
  const inputId = id || `input-${props.name || Math.random().toString(36).substring(7)}`;

  return (
    <div className={`mb-4 ${containerClassName || ''}`}>
      <label htmlFor={inputId} className="block text-sm font-medium text-slate-700 mb-1">
        {label}
      </label>
      <input
        id={inputId}
        type={type}
        onFocus={() => setIsFocused(true)}
        onBlurCapture={() => setIsFocused(false)}
        className={`mt-1 block w-full px-3 py-2 bg-white border ${
          error ? 'border-red-500 focus:ring-red-500 focus:border-red-500' : 'border-slate-300 focus:ring-sky-500 focus:border-sky-500'
        } rounded-md shadow-sm placeholder-slate-400 
        focus:outline-none sm:text-sm transition-all duration-150
        ${isFocused ? 'ring-2 ring-opacity-50' : ''}`}
        {...props}
      />
      {error && <p className="mt-1 text-xs text-red-600">{error}</p>}
    </div>
  );
};

export default Input;
