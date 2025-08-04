import React, { createContext, useContext, useState, useEffect } from 'react';

/**
 * Theme Context for managing light/dark theme state across the application
 * 
 * Provides theme state management with localStorage persistence.
 * Defaults to dark theme to maintain current user experience.
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
const ThemeContext = createContext();

export const useTheme = () => {
  const context = useContext(ThemeContext);
  if (!context) {
    throw new Error('useTheme must be used within a ThemeProvider');
  }
  return context;
};

export const ThemeProvider = ({ children }) => {
  // Initialize theme from localStorage or default to 'dark'
  const [theme, setTheme] = useState(() => {
    const savedTheme = localStorage.getItem('aircraft-monitoring-theme');
    return savedTheme || 'dark';
  });

  // Update localStorage when theme changes
  useEffect(() => {
    localStorage.setItem('aircraft-monitoring-theme', theme);
    
    // Update document class for global theme styling
    if (theme === 'dark') {
      document.documentElement.classList.add('dark');
      document.documentElement.classList.remove('light');
    } else {
      document.documentElement.classList.add('light');
      document.documentElement.classList.remove('dark');
    }
  }, [theme]);

  const toggleTheme = () => {
    setTheme(prevTheme => prevTheme === 'dark' ? 'light' : 'dark');
  };

  const value = {
    theme,
    toggleTheme,
    isDark: theme === 'dark',
    isLight: theme === 'light'
  };

  return (
    <ThemeContext.Provider value={value}>
      {children}
    </ThemeContext.Provider>
  );
};

export default ThemeContext;