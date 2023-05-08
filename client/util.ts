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
