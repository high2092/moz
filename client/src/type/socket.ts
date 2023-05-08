export interface SocketPayload {
  type: 'chat/local' | 'system';
  body: string;
  from?: string;
}
