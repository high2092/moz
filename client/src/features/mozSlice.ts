import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { SocketPayload, SocketPayloadTypes } from '../type/socket';
import { Quiz, QuizTypes } from '../type/quiz';
import { QuizBundle } from '../type/quizBundle';

interface MozState {
  socket: WebSocket;
  chatList: SocketPayload[];
  isReady: boolean;
  quizzes: Record<number, Quiz>;
  currentRoundQuiz: Quiz;
  quizBundles: Record<number, QuizBundle>;
}

const initialState: MozState = {
  socket: null,
  chatList: [],
  isReady: false,
  quizzes: {},
  currentRoundQuiz: null,
  quizBundles: {},
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
      action.payload.forEach((quiz) => {
        state.quizzes[quiz.id] = quiz;
      });
    },

    addQuiz(state, action: PayloadAction<Quiz>) {
      const quiz = action.payload;
      state.quizzes[quiz.id] = quiz;
    },

    removeQuiz(state, action: PayloadAction<number>) {
      delete state.quizzes[action.payload];
    },

    toggleSelectQuiz(state, action: PayloadAction<number>) {
      const target = state.quizzes[action.payload];
      target.selected = !target.selected;
    },

    fetchQuizBundleList(state, action: PayloadAction<QuizBundle[]>) {
      action.payload.forEach((quizBundle) => {
        state.quizBundles[quizBundle.id] = quizBundle;
      });
    },
  },
});

export const { initSocket, receiveMessage, ready, unready, fetchQuiz, addQuiz, removeQuiz, toggleSelectQuiz, fetchQuizBundleList } = mozSlice.actions;
