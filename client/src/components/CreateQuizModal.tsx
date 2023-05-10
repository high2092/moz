import { httpPostApi } from '../util';
import { PreparedModalProps } from '../type/modal';
import { Quiz, QuizTypes } from '../type/quiz';
import * as S from './CreateQuizModal.style';
import { CenteredModal } from './Modal';
import { useForm, FieldValues } from 'react-hook-form';
import { useAppDispatch } from '../store';
import { addQuiz } from '../features/mozSlice';

export const CreateQuizModal = ({ zIndex }: PreparedModalProps) => {
  return <CenteredModal content={<CreateQuizModalContent />} zIndex={zIndex} />;
};

function CreateQuizModalContent() {
  const dispatch = useAppDispatch();
  const { register, handleSubmit } = useForm();

  const handleCreateQuiz = async (formData: FieldValues) => {
    const { consonant, answer } = formData;
    const quiz: Quiz = { type: QuizTypes.CONSONANT, consonant, answer };
    const response = await httpPostApi('quiz', quiz);
    if (!response.ok) {
      console.error(response.statusText);
      return;
    }

    dispatch(addQuiz(quiz));
  };

  return (
    <S.CreateQuizModal>
      <S.CreateQuizForm onSubmit={handleSubmit(handleCreateQuiz)}>
        <input {...register('consonant')} placeholder="초성" />
        <input {...register('answer')} placeholder="정답" />
        <button>퀴즈 생성</button>
      </S.CreateQuizForm>
    </S.CreateQuizModal>
  );
}
