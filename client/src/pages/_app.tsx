import '../../public/css/reset.css';
import { Provider } from 'react-redux';
import { store } from '../store';

const App = ({ Component, pageProps }) => {
  return (
    <Provider store={store}>
      <Component {...pageProps} />
    </Provider>
  );
};

export default App;
