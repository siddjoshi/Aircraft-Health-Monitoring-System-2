import React from 'react';
import { ThemeProvider } from './contexts/ThemeContext';
import ThemeToggle from './components/ThemeToggle';
import { useThemeClasses } from './hooks/useThemeClasses';

// Mock aircraft data for demo
const mockAircraftData = {
  timestamp: new Date().toISOString(),
  engineRPM: 2450,
  engineTemperature: 185.2,
  oilPressure: 45.8,
  oilTemperature: 95.3,
  fuelLevel: 75.3,
  fuelConsumption: 125.7,
  fuelPressure: 32.1,
  fuelTemperature: 22.5,
  hydraulicPressure: 3200,
  hydraulicTemperature: 45.8,
  fluidLevel: 85.2,
  altitude: 35000,
  airspeed: 520,
  groundSpeed: 485,
  machNumber: 0.82,
  verticalSpeed: 0,
  cabinPressure: 11.3,
  cabinTemperature: 22.5,
  batteryVoltage: 24.8,
  generatorOutput: 28.5,
  // Anomaly flags
  engineAnomaly: false,
  fuelAnomaly: false,
  hydraulicAnomaly: false,
  altitudeAnomaly: false,
  airspeedAnomaly: false
};

const DemoApp = () => {
  const { card, textPrimary, textSecondary, background } = useThemeClasses();

  return (
    <div className={`min-h-screen ${background} ${textPrimary}`}>
      {/* Header */}
      <header className={`${card} border-b`}>
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-4">
            <div className="flex items-center space-x-3">
              <div className="w-8 h-8 bg-aviation-blue rounded-lg flex items-center justify-center">
                <span className="text-white font-bold text-sm">✈</span>
              </div>
              <div>
                <h1 className={`text-xl font-bold ${textPrimary}`}>
                  Aircraft Health Monitoring System
                </h1>
                <p className={`text-sm ${textSecondary}`}>
                  Real-Time Aviation Dashboard - Complete Theme Demo
                </p>
              </div>
            </div>
            <ThemeToggle />
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        {/* Theme Demo Instructions */}
        <div className={`${card} p-6 mb-6`}>
          <h2 className={`text-lg font-semibold ${textPrimary} mb-3`}>
            🎨 Light/Dark Theme Toggle Demo
          </h2>
          <p className={`${textSecondary} mb-3`}>
            This is a complete implementation of the light/dark theme toggle for the Aircraft Health Monitoring Dashboard.
            Click the theme toggle button in the top-right corner to switch between themes.
          </p>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mt-4">
            <div className="flex items-center space-x-2">
              <span className="px-2 py-1 rounded bg-aviation-green text-white text-xs">✓</span>
              <span className={textPrimary}>Theme Context & Provider</span>
            </div>
            <div className="flex items-center space-x-2">
              <span className="px-2 py-1 rounded bg-aviation-green text-white text-xs">✓</span>
              <span className={textPrimary}>localStorage Persistence</span>
            </div>
            <div className="flex items-center space-x-2">
              <span className="px-2 py-1 rounded bg-aviation-green text-white text-xs">✓</span>
              <span className={textPrimary}>Smooth Transitions</span>
            </div>
            <div className="flex items-center space-x-2">
              <span className="px-2 py-1 rounded bg-aviation-green text-white text-xs">✓</span>
              <span className={textPrimary}>All Components Updated</span>
            </div>
            <div className="flex items-center space-x-2">
              <span className="px-2 py-1 rounded bg-aviation-green text-white text-xs">✓</span>
              <span className={textPrimary}>Aviation Colors Preserved</span>
            </div>
            <div className="flex items-center space-x-2">
              <span className="px-2 py-1 rounded bg-aviation-green text-white text-xs">✓</span>
              <span className={textPrimary}>Utility Hook for Consistency</span>
            </div>
          </div>
        </div>

        {/* Sample Dashboard Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {/* Engine System Demo */}
          <div className={`${card} p-6`}>
            <div className="flex items-center justify-between mb-4">
              <h3 className={`text-lg font-semibold ${textPrimary} flex items-center`}>
                <span className="text-aviation-yellow mr-2">⚡</span>
                Engine System
              </h3>
              <div className="px-2 py-1 rounded-full text-xs font-medium bg-aviation-green text-white">
                NORMAL
              </div>
            </div>
            <div className="space-y-3">
              <div className="flex items-center justify-between">
                <span className={`text-sm ${textSecondary}`}>Engine RPM</span>
                <div className="text-right">
                  <div className="text-lg font-mono font-semibold text-aviation-green">
                    {mockAircraftData.engineRPM}
                  </div>
                  <div className={`text-xs ${textSecondary}`}>RPM</div>
                </div>
              </div>
              <div className="flex items-center justify-between">
                <span className={`text-sm ${textSecondary}`}>Temperature</span>
                <div className="text-right">
                  <div className="text-lg font-mono font-semibold text-aviation-green">
                    {mockAircraftData.engineTemperature}
                  </div>
                  <div className={`text-xs ${textSecondary}`}>°C</div>
                </div>
              </div>
            </div>
          </div>

          {/* Fuel System Demo */}
          <div className={`${card} p-6`}>
            <div className="flex items-center justify-between mb-4">
              <h3 className={`text-lg font-semibold ${textPrimary} flex items-center`}>
                <span className="text-aviation-blue mr-2">⛽</span>
                Fuel System
              </h3>
              <div className="px-2 py-1 rounded-full text-xs font-medium bg-aviation-green text-white">
                NORMAL
              </div>
            </div>
            <div className="space-y-3">
              <div className="flex items-center justify-between">
                <span className={`text-sm ${textSecondary}`}>Fuel Level</span>
                <div className="text-right">
                  <div className="text-lg font-mono font-semibold text-aviation-green">
                    {mockAircraftData.fuelLevel}
                  </div>
                  <div className={`text-xs ${textSecondary}`}>%</div>
                </div>
              </div>
              <div className="w-full bg-gray-300 dark:bg-gray-700 rounded-full h-2 mt-2">
                <div 
                  className="h-2 rounded-full bg-aviation-green"
                  style={{ width: `${mockAircraftData.fuelLevel}%` }}
                ></div>
              </div>
            </div>
          </div>

          {/* Flight Data Demo */}
          <div className={`${card} p-6`}>
            <div className="flex items-center justify-between mb-4">
              <h3 className={`text-lg font-semibold ${textPrimary} flex items-center`}>
                <span className="text-aviation-green mr-2">✈️</span>
                Flight Data
              </h3>
              <div className="px-2 py-1 rounded-full text-xs font-medium bg-aviation-green text-white">
                NORMAL
              </div>
            </div>
            <div className="space-y-3">
              <div className="flex items-center justify-between">
                <span className={`text-sm ${textSecondary}`}>Altitude</span>
                <div className="text-right">
                  <div className="text-lg font-mono font-semibold text-aviation-green">
                    {mockAircraftData.altitude.toLocaleString()}
                  </div>
                  <div className={`text-xs ${textSecondary}`}>ft</div>
                </div>
              </div>
              <div className="flex items-center justify-between">
                <span className={`text-sm ${textSecondary}`}>Airspeed</span>
                <div className="text-right">
                  <div className="text-lg font-mono font-semibold text-aviation-green">
                    {mockAircraftData.airspeed}
                  </div>
                  <div className={`text-xs ${textSecondary}`}>kts</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Implementation Summary */}
        <div className={`${card} p-6 mt-6`}>
          <h3 className={`text-lg font-semibold ${textPrimary} mb-4`}>
            🔧 Implementation Summary
          </h3>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <h4 className={`font-medium ${textPrimary} mb-2`}>Files Added/Modified:</h4>
              <ul className={`text-sm ${textSecondary} space-y-1`}>
                <li>• <code>src/contexts/ThemeContext.js</code> - Theme state management</li>
                <li>• <code>src/hooks/useThemeClasses.js</code> - Theme utility hook</li>
                <li>• <code>src/components/ThemeToggle.js</code> - Toggle button component</li>
                <li>• <code>tailwind.config.js</code> - Dark mode & light theme colors</li>
                <li>• <code>src/App.css</code> - Light/dark scrollbar styles</li>
              </ul>
            </div>
            <div>
              <h4 className={`font-medium ${textPrimary} mb-2`}>Components Updated:</h4>
              <ul className={`text-sm ${textSecondary} space-y-1`}>
                <li>• <code>App.js</code> - Theme provider & toggle integration</li>
                <li>• <code>Dashboard.js</code> - Theme-aware styling</li>
                <li>• <code>SystemStatus.js</code> - Complete theme support</li>
                <li>• <code>EngineSystem.js</code> - Theme-aware styling</li>
                <li>• <code>FuelSystem.js</code> - Theme-aware styling</li>
                <li>• <code>HydraulicSystem.js</code> - Theme-aware styling</li>
                <li>• <code>FlightData.js</code> - Theme-aware styling</li>
                <li>• <code>AnomalyControls.js</code> - Theme-aware styling</li>
                <li>• <code>AlertPanel.js</code> - Theme-aware styling</li>
              </ul>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

const ThemeDemo = () => {
  return (
    <ThemeProvider>
      <DemoApp />
    </ThemeProvider>
  );
};

export default ThemeDemo;