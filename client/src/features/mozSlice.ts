import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SocketPayload, SocketPayloadTypes } from '../type/socket';
import { Quiz, QuizTypes } from '../type/quiz';

interface MozState {
  socket: WebSocket;
  chatList: SocketPayload[];
  isReady: boolean;
  quizList: Quiz[];
  currentRoundQuiz: Quiz;
}

const initialState: MozState = {
  socket: null,
  chatList: [],
  isReady: false,
  quizList: [],
  currentRoundQuiz: null,
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
      const payload = action.payload;
      switch (payload.type) {
        case SocketPayloadTypes.SYSTEM:
        case SocketPayloadTypes.LOCAL_CHAT: {
          state.chatList = [...state.chatList, action.payload];
          break;
        }
        case SocketPayloadTypes.MUSIC_QUIZ: {
          state.currentRoundQuiz = { type: QuizTypes.MUSIC, question: action.payload.body };
          break;
        }
        case SocketPayloadTypes.ROUND_INFO: {
          state.currentRoundQuiz = { type: QuizTypes.CONSONANT, question: action.payload.body };
          break;
        }
        case SocketPayloadTypes.GAME_OVER: {
          state.isReady = false;
          state.chatList = [...state.chatList, action.payload];
          break;
        }
      }
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

    removeQuiz(state, action: PayloadAction<number>) {
      state.quizList = state.quizList.filter(({ id }) => id !== action.payload);
    },

    toggleSelectQuiz(state, action: PayloadAction<number>) {
      const target = state.quizList.find(({ id }) => id === action.payload);
      target.selected = !target.selected;
    },
  },
});

export const { initSocket, receiveMessage, ready, unready, fetchQuiz, addQuiz, removeQuiz, toggleSelectQuiz } = mozSlice.actions;
