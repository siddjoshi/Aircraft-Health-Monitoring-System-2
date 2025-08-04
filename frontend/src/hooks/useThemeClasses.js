import { useTheme } from '../contexts/ThemeContext';

/**
 * Custom hook for theme-aware CSS classes
 * 
 * Provides commonly used theme-aware class combinations
 * to maintain consistency across components.
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
export const useThemeClasses = () => {
  const { isDark } = useTheme();

  return {
    // Card background and border
    card: `border rounded-lg transition-colors duration-200 ${
      isDark 
        ? 'bg-card-bg border-border-color' 
        : 'bg-card-bg-light border-border-color-light'
    }`,
    
    // Primary text
    textPrimary: isDark ? 'text-white' : 'text-text-primary-light',
    
    // Secondary text (subtitles, descriptions)
    textSecondary: isDark ? 'text-gray-400' : 'text-text-secondary-light',
    
    // Muted text (very light)
    textMuted: isDark ? 'text-gray-500' : 'text-gray-500',
    
    // Background for main content areas
    background: `transition-colors duration-200 ${
      isDark 
        ? 'bg-dashboard-bg' 
        : 'bg-dashboard-bg-light'
    }`,
    
    // Input and interactive elements
    input: `border rounded transition-colors duration-200 ${
      isDark 
        ? 'bg-card-bg border-border-color text-white' 
        : 'bg-white border-border-color-light text-text-primary-light'
    }`,
    
    // Button variations
    button: {
      primary: `bg-aviation-blue hover:bg-blue-700 text-white transition-colors duration-200`,
      secondary: `transition-colors duration-200 ${
        isDark 
          ? 'bg-slate-700 hover:bg-slate-600 text-white' 
          : 'bg-slate-200 hover:bg-slate-300 text-text-primary-light'
      }`,
    }
  };
};

export default useThemeClasses;