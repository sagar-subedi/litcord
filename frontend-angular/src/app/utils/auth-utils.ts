export class AuthUtils {
    /**
     * Extracts the subject (email) from a JWT token.
     * @param token The JWT token
     * @returns The email (subject) or null if invalid
     */
    static extractEmailFromToken(token: string): string | null {
      try {
        const payload = token.split('.')[1];
        const decodedPayload = atob(payload);
        const payloadObject = JSON.parse(decodedPayload);
        return payloadObject.sub || null;
      } catch (error) {
        console.error('Failed to extract email from token:', error);
        return null;
      }
    }

    static extractUserIdFromToken(token: string): string | null {
        try {
          const payload = token.split('.')[1];
          const decodedPayload = atob(payload);
          const payloadObject = JSON.parse(decodedPayload);
          return payloadObject.userId || null;
        } catch (error) {
          console.error('Failed to extract userId from token:', error);
          return null;
        }
      }
  }
  