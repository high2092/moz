import * as S from './CreateQuizBundleModal.style';
import { useAppSelector } from '../store';
import { PreparedModalProps } from '../type/modal';
import { CenteredModal } from './Modal';
import { httpPostApi } from '../util';
import { FieldValues, useForm } from 'react-hook-form';

export const CreateQuizBundleModal = ({ zIndex }: PreparedModalProps) => {
  return <CenteredModal content={<CreateQuizBundleModalContent />} zIndex={zIndex} />;
};

function CreateQuizBundleModalContent() {
  const { quizList } = useAppSelector((state) => state.moz);
  const { register, handleSubmit } = useForm();

  const selectedQuizList = quizList.filter(({ selected }) => selected); // TODO: createSelector

  const handleQuizCreateQuizBundle = async ({ title }: FieldValues) => {
    const response = await httpPostApi('quiz-bundle', { title, quizzes: selectedQuizList.map(({ id }) => id) });
    if (!response.ok) {
      console.error(response.statusText);
      return;
    }
  };

  return (
    <S.CreateQuizBundleModal>
      <form onSubmit={handleSubmit(handleQuizCreateQuizBundle)} style={{ display: 'flex' }}>
        <div>
          <input {...register('title')} placeholder="문제집 이름" />
          <div>
            {selectedQuizList[0].answers[0].answer} 외 {quizList.length - 1}개
          </div>
        </div>
        <button>만들기</button>
      </form>
    </S.CreateQuizBundleModal>
  );
}
