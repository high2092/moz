/** @jsxImportSource @emotion/react */
import { modalStyle } from '../styles/modalStyle';
import { useAppSelector } from '../store';

export const QuizBundleListModalContent = () => {
  const { quizBundleList } = useAppSelector((state) => state.moz);

  return (
    <div css={modalStyle}>
      <div>
        {quizBundleList.map(({ id, title }) => (
          <div key={`quizBundle-${id}`}>
            {id} {title}
          </div>
        ))}
      </div>
    </div>
  );
};
