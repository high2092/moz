import { SocketPayload } from './src/type/socket';
import { User } from './src/type/user';

export function cutUserListInHalf(users: User[]) {
  return [users.filter((user, idx) => idx % 2 === 0), users.filter((user, idx) => idx % 2 === 1)];
}

export function sendMessage(payload: SocketPayload, socket: WebSocket) {
  if (socket !== null && socket.readyState === WebSocket.OPEN) {
    socket.send(JSON.stringify(payload));
  }
}

export function convertPayloadToChat({ type, body, from }: SocketPayload) {
  switch (type) {
    case 'chat/local': {
      return `${from}: ${body}`;
    }

    case 'system': {
      return `[system] ${body}`;
    }

    default: {
      return `[${type}] ${from}: ${body}`;
    }
  }
}
