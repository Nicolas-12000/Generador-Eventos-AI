import React from "react";

interface CardProps {
  children: React.ReactNode;
  className?: string;
}

interface CardContentProps {
  children: React.ReactNode;
  className?: string;
}

export const Card = ({ children, className = "" }: CardProps) => {
  return (
    <div className={`bg-white shadow-md rounded-lg ${className}`}>
      {children}
    </div>
  );
};

export const CardContent = ({ children, className = "" }: CardContentProps) => {
  return <div className={`p-4 ${className}`}>{children}</div>;
};