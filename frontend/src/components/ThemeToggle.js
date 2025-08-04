import React from 'react';
import { Sun, Moon } from 'lucide-react';
import { useTheme } from '../contexts/ThemeContext';

/**
 * Theme Toggle Component
 * 
 * Provides a button to switch between light and dark themes.
 * Shows appropriate icon based on current theme state.
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
const ThemeToggle = () => {
  const { theme, toggleTheme, isDark } = useTheme();

  return (
    <button
      onClick={toggleTheme}
      className={`
        flex items-center justify-center w-10 h-10 rounded-lg
        transition-all duration-200 ease-in-out
        ${isDark 
          ? 'bg-slate-700 hover:bg-slate-600 text-yellow-400' 
          : 'bg-slate-200 hover:bg-slate-300 text-slate-700'
        }
        hover:scale-105 active:scale-95
      `}
      title={`Switch to ${isDark ? 'light' : 'dark'} theme`}
      aria-label={`Switch to ${isDark ? 'light' : 'dark'} theme`}
    >
      {isDark ? (
        <Sun className="w-5 h-5" />
      ) : (
        <Moon className="w-5 h-5" />
      )}
    </button>
  );
};

export default ThemeToggle;