import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SocketPayload } from '../type/socket';

interface MozState {
  socket: WebSocket;
  chatList: SocketPayload[];
  isReady: boolean;
}

const initialState: MozState = {
  socket: null,
  chatList: [],
  isReady: false,
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

    ready(state) {
      state.isReady = true;
    },

    unready(state) {
      state.isReady = false;
    },
  },
});

export const { initSocket, receiveMessage, ready, unready } = mozSlice.actions;
