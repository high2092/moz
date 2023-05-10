import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SocketPayload } from '../type/socket';
import { Quiz } from '../type/quiz';

interface MozState {
  socket: WebSocket;
  chatList: SocketPayload[];
  isReady: boolean;
  quizList: Quiz[];
}

const initialState: MozState = {
  socket: null,
  chatList: [],
  isReady: false,
  quizList: [],
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

    fetchQuiz(state, action: PayloadAction<Quiz[]>) {
      state.quizList = action.payload;
    },

    addQuiz(state, action: PayloadAction<Quiz>) {
      state.quizList.push(action.payload);
    },
  },
});

export const { initSocket, receiveMessage, ready, unready, fetchQuiz, addQuiz } = mozSlice.actions;
