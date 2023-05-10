import styled from '@emotion/styled';
import { MODAL_DEFAULT_BORDER_RADIUS } from '../../constants';

export const CreateQuizModal = styled.div`
  padding: 2rem;
`;

export const CreateQuizForm = styled.form`
  padding: 2rem;

  background-color: white;

  border-radius: ${MODAL_DEFAULT_BORDER_RADIUS};

  display: flex;
  flex-direction: column;
`;
