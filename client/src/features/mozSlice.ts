import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SocketPayload } from '../type/socket';

interface MozState {
  socket: WebSocket;
  chatList: SocketPayload[];
}

const initialState: MozState = {
  socket: null,
  chatList: [],
};

export const mozSlice = createSlice({
  name: 'moz',
  initialState,
  reducers: {
    initSocket(state, action: PayloadAction<WebSocket>) {
      if (state.socket !== null) return;
      state.socket = action.payload;
    },

    receiveMessage(state, action: PayloadAction<SocketPayload>) {
      state.chatList = [...state.chatList, action.payload];
    },
  },
});

export const { initSocket, receiveMessage } = mozSlice.actions;
