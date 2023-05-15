import styled from '@emotion/styled';
import { MODAL_DEFAULT_BORDER_RADIUS } from '../../constants';

export const QuizListModal = styled.div`
  padding: 2rem;

  background-color: white;

  border-radius: ${MODAL_DEFAULT_BORDER_RADIUS};
`;

export const DeleteButton = styled.div`
  color: #820000;
  font-size: 0.8rem;

  cursor: pointer;
`;
