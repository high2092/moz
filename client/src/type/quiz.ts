export const QuizTypes = {
  CONSONANT: 'consonant',
} as const;

export type QuizType = (typeof QuizTypes)[keyof typeof QuizTypes];

export interface Quiz {
  id?: number;
  type: QuizType;
  consonant: string;
  answer: Answer;
}

interface Answer {
  answer: string;
  score: number;
}
