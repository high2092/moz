import { httpPostApi } from '../util';
import { PreparedModalProps } from '../type/modal';
import { Quiz, QuizType, QuizTypes } from '../type/quiz';
import * as S from './CreateQuizModal.style';
import { CenteredModal } from './Modal';
import { useForm, FieldValues } from 'react-hook-form';
import { useAppDispatch } from '../store';
import { addQuiz } from '../features/mozSlice';
import { RadioGroup } from './RadioGroup';
import { useState } from 'react';

export const CreateQuizModal = ({ zIndex }: PreparedModalProps) => {
  return <CenteredModal content={<CreateQuizModalContent />} zIndex={zIndex} />;
};

function CreateQuizModalContent() {
  const dispatch = useAppDispatch();
  const { register, handleSubmit } = useForm();

  const [quizType, setQuizType] = useState<QuizType>(QuizTypes.CONSONANT);

  const getQuestion = (type: QuizType, { consonant, videoId }) => {
    switch (type) {
      case QuizTypes.CONSONANT:
        return consonant;
      case QuizTypes.MUSIC:
        return videoId;
    }
  };

  const handleCreateQuiz = async (formData: FieldValues) => {
    const { consonant, videoId, answer } = formData;
    const quiz: Quiz = { type: quizType, question: getQuestion(quizType, { consonant, videoId }), answer };
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
        <RadioGroup
          name="quizType"
          options={[
            { value: QuizTypes.CONSONANT, label: '초성' },
            { value: QuizTypes.MUSIC, label: '음악' },
          ]}
          currentValue={quizType}
          setCurrentValue={(value: QuizType) => setQuizType(value)}
        />
        {quizType === QuizTypes.CONSONANT && <input {...register('consonant')} placeholder="초성" />}
        {quizType === QuizTypes.MUSIC && <input {...register('videoId')} placeholder="비디오 ID" />}
        <input {...register('answer')} placeholder="정답" />
        <button>퀴즈 생성</button>
      </S.CreateQuizForm>
    </S.CreateQuizModal>
  );
}
