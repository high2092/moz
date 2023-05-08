import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface MozState {
  socket: WebSocket;
  chatList: string[];
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

    receiveMessage(state, action: PayloadAction<string>) {
      state.chatList = [...state.chatList, action.payload];
    },
  },
});

export const { initSocket, receiveMessage } = mozSlice.actions;
