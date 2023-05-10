export const ModalTypes = {
  CREATE_QUIZ: 'createQuiz',
} as const;

export type ModalType = (typeof ModalTypes)[keyof typeof ModalTypes];

export interface PreparedModalProps {
  zIndex: number;
}
