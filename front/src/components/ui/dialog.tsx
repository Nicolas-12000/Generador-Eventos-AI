import React, { useState } from "react";

interface DialogProps {
  children: React.ReactNode;
}

interface DialogTriggerProps {
  children: React.ReactNode;
  onClick?: () => void;
}

interface DialogContentProps {
  children: React.ReactNode;
}

export const Dialog = ({ children }: DialogProps) => {
  return <>{children}</>;
};

export const DialogTrigger = ({ children, onClick }: DialogTriggerProps) => {
  return <div onClick={onClick}>{children}</div>;
};

export const DialogContent = ({ children }: DialogContentProps) => {
  return (
    <div className="bg-white border border-gray-300 rounded-md p-4 max-w-sm mx-auto shadow-md">
      {children}
    </div>
  );
};