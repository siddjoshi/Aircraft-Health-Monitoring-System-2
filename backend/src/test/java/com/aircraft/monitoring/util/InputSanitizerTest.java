package com.aircraft.monitoring.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InputSanitizer utility.
 * 
 * Tests cover:
 * - XSS attack prevention
 * - Script injection detection
 * - HTML tag removal
 * - Alert type sanitization
 * - Severity level validation
 * 
 * @author Aircraft Monitoring Team
 * @version 1.0.0
 */
class InputSanitizerTest {

    private InputSanitizer inputSanitizer;

    @BeforeEach
    void setUp() {
        inputSanitizer = new InputSanitizer();
    }

    /**
     * Test sanitizing normal text
     */
    @Test
    void sanitizeText_NormalText_ShouldReturnUnchanged() {
        String input = "Engine temperature is high";
        String result = inputSanitizer.sanitizeText(input);
        assertEquals("Engine temperature is high", result);
    }

    /**
     * Test sanitizing text with HTML tags
     */
    @Test
    void sanitizeText_WithHtmlTags_ShouldRemoveTags() {
        String input = "Engine <b>temperature</b> is <i>high</i>";
        String result = inputSanitizer.sanitizeText(input);
        assertEquals("Engine temperature is high", result);
    }

    /**
     * Test sanitizing text with script tags
     */
    @Test
    void sanitizeText_WithScriptTags_ShouldRemoveScript() {
        String input = "Alert <script>alert('XSS')</script> message";
        String result = inputSanitizer.sanitizeText(input);
        assertEquals("Alert message", result);
    }

    /**
     * Test sanitizing text with dangerous characters
     */
    @Test
    void sanitizeText_WithDangerousChars_ShouldRemoveChars() {
        String input = "Alert <>&\"' message";
        String result = inputSanitizer.sanitizeText(input);
        assertEquals("Alert message", result);
    }

    /**
     * Test sanitizing text with javascript URL
     */
    @Test
    void sanitizeText_WithJavascriptUrl_ShouldRemoveJavascript() {
        String input = "Click javascript:alert('XSS') here";
        String result = inputSanitizer.sanitizeText(input);
        assertEquals("Click alert(XSS) here", result);
    }

    /**
     * Test sanitizing null input
     */
    @Test
    void sanitizeText_NullInput_ShouldReturnNull() {
        String result = inputSanitizer.sanitizeText(null);
        assertNull(result);
    }

    /**
     * Test sanitizing text with excessive whitespace
     */
    @Test
    void sanitizeText_WithExcessiveWhitespace_ShouldNormalizeSpaces() {
        String input = "Engine    temperature   is    high   ";
        String result = inputSanitizer.sanitizeText(input);
        assertEquals("Engine temperature is high", result);
    }

    /**
     * Test sanitizing valid alert type
     */
    @Test
    void sanitizeAlertType_ValidType_ShouldReturnUppercase() {
        String input = "engine";
        String result = inputSanitizer.sanitizeAlertType(input);
        assertEquals("ENGINE", result);
    }

    /**
     * Test sanitizing alert type with invalid characters
     */
    @Test
    void sanitizeAlertType_WithInvalidChars_ShouldRemoveInvalidChars() {
        String input = "engine123!@#";
        String result = inputSanitizer.sanitizeAlertType(input);
        assertEquals("ENGINE", result);
    }

    /**
     * Test sanitizing alert type with underscores
     */
    @Test
    void sanitizeAlertType_WithUnderscores_ShouldKeepUnderscores() {
        String input = "engine_temp";
        String result = inputSanitizer.sanitizeAlertType(input);
        assertEquals("ENGINE_TEMP", result);
    }

    /**
     * Test sanitizing null alert type
     */
    @Test
    void sanitizeAlertType_NullInput_ShouldReturnNull() {
        String result = inputSanitizer.sanitizeAlertType(null);
        assertNull(result);
    }

    /**
     * Test sanitizing valid severity levels
     */
    @Test
    void sanitizeSeverity_ValidLevels_ShouldReturnCorrectLevel() {
        assertEquals("INFO", inputSanitizer.sanitizeSeverity("info"));
        assertEquals("WARNING", inputSanitizer.sanitizeSeverity("warning"));
        assertEquals("CRITICAL", inputSanitizer.sanitizeSeverity("critical"));
        assertEquals("INFO", inputSanitizer.sanitizeSeverity("INFO"));
        assertEquals("WARNING", inputSanitizer.sanitizeSeverity("WARNING"));
        assertEquals("CRITICAL", inputSanitizer.sanitizeSeverity("CRITICAL"));
    }

    /**
     * Test sanitizing invalid severity levels
     */
    @Test
    void sanitizeSeverity_InvalidLevels_ShouldReturnInfo() {
        assertEquals("INFO", inputSanitizer.sanitizeSeverity("invalid"));
        assertEquals("INFO", inputSanitizer.sanitizeSeverity("error"));
        assertEquals("INFO", inputSanitizer.sanitizeSeverity("debug"));
        assertEquals("INFO", inputSanitizer.sanitizeSeverity(""));
    }

    /**
     * Test sanitizing null severity
     */
    @Test
    void sanitizeSeverity_NullInput_ShouldReturnInfo() {
        String result = inputSanitizer.sanitizeSeverity(null);
        assertEquals("INFO", result);
    }

    /**
     * Test input safety check with safe text
     */
    @Test
    void isInputSafe_SafeText_ShouldReturnTrue() {
        assertTrue(inputSanitizer.isInputSafe("Engine temperature is high"));
        assertTrue(inputSanitizer.isInputSafe("Fuel level at 25%"));
        assertTrue(inputSanitizer.isInputSafe("Normal operation"));
        assertTrue(inputSanitizer.isInputSafe(null));
    }

    /**
     * Test input safety check with dangerous script content
     */
    @Test
    void isInputSafe_WithScript_ShouldReturnFalse() {
        assertFalse(inputSanitizer.isInputSafe("Alert <script>alert('XSS')</script>"));
        assertFalse(inputSanitizer.isInputSafe("javascript:alert('XSS')"));
        assertFalse(inputSanitizer.isInputSafe("vbscript:msgbox('XSS')"));
    }

    /**
     * Test input safety check with dangerous HTML elements
     */
    @Test
    void isInputSafe_WithDangerousHtml_ShouldReturnFalse() {
        assertFalse(inputSanitizer.isInputSafe("Click <iframe src='evil.com'>"));
        assertFalse(inputSanitizer.isInputSafe("Submit <form action='evil.com'>"));
        assertFalse(inputSanitizer.isInputSafe("Load <object data='evil.com'>"));
    }

    /**
     * Test input safety check with event handlers
     */
    @Test
    void isInputSafe_WithEventHandlers_ShouldReturnFalse() {
        assertFalse(inputSanitizer.isInputSafe("Click onload='alert(1)'"));
        assertFalse(inputSanitizer.isInputSafe("Error onerror='alert(1)'"));
    }

    /**
     * Test input safety check with server-side includes
     */
    @Test
    void isInputSafe_WithServerSideIncludes_ShouldReturnFalse() {
        assertFalse(inputSanitizer.isInputSafe("Include <?php echo 'evil'; ?>"));
        assertFalse(inputSanitizer.isInputSafe("Server <% Response.Write('evil') %>"));
        assertFalse(inputSanitizer.isInputSafe("Comment <!-- evil -->"));
    }

    /**
     * Test input safety check with complex dangerous content
     */
    @Test
    void isInputSafe_WithComplexDangerousContent_ShouldReturnFalse() {
        assertFalse(inputSanitizer.isInputSafe("document.cookie = 'stolen'"));
        assertFalse(inputSanitizer.isInputSafe("eval('malicious code')"));
        assertFalse(inputSanitizer.isInputSafe("expression(alert('XSS'))"));
    }
}