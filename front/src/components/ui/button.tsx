import React from "react";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

export const Button = ({ children, ...props }: ButtonProps) => {
  return (
    <button
      className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700 transition"
      {...props}
    >
      {children}
    </button>
  );
};