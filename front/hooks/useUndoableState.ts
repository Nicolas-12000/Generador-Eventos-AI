
import { useState, useCallback } from 'react';

interface UndoableState<T> {
  current: T;
  undo: () => void;
  redo: () => void;
  set: (newState: T) => void;
  reset: (initialState: T) => void;
  canUndo: boolean;
  canRedo: boolean;
}

function useUndoableState<T,>(initialState: T): UndoableState<T> {
  const [history, setHistory] = useState<T[]>([initialState]);
  const [currentIndex, setCurrentIndex] = useState(0);

  const current = history[currentIndex];
  const canUndo = currentIndex > 0;
  const canRedo = currentIndex < history.length - 1;

  const set = useCallback((newState: T) => {
    const newHistory = history.slice(0, currentIndex + 1);
    newHistory.push(newState);
    setHistory(newHistory);
    setCurrentIndex(newHistory.length - 1);
  }, [history, currentIndex]);

  const undo = useCallback(() => {
    if (canUndo) {
      setCurrentIndex(currentIndex - 1);
    }
  }, [canUndo, currentIndex]);

  const redo = useCallback(() => {
    if (canRedo) {
      setCurrentIndex(currentIndex + 1);
    }
  }, [canRedo, currentIndex]);
  
  const reset = useCallback((newInitialState: T) => {
    setHistory([newInitialState]);
    setCurrentIndex(0);
  }, []);

  return { current, set, reset, undo, redo, canUndo, canRedo };
}

export default useUndoableState;
