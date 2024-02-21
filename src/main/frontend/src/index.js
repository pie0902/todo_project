import React from 'react';
import ReactDOM from 'react-dom/client'; // React 18의 새로운 import 방식
import { BrowserRouter as Router } from 'react-router-dom'; // Router import
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import './index.css';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </React.StrictMode>,
);
