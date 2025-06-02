import React from "react";

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  className?: string;
}

export const Input = ({ className = "", ...props }: InputProps) => {
  return (
    <input
      className={`border border-gray-300 rounded px-3 py-2 focus:outline-none focus:border-blue-500 ${className}`}
      {...props}
    />
  );
};