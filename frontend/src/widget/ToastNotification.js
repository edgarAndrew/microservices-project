// src/components/ToastNotification.js

import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const ToastNotification = ({ message, show, onClose }) => {
  if (!show) return null;

  return (
    <div className="toast-container position-fixed bottom-0 end-0 p-3">
      <div className="toast show" role="alert" aria-live="assertive" aria-atomic="true">
        <div className="toast-header">
          <strong className="me-auto">Error</strong>
          <button type="button" className="btn-close" onClick={onClose}></button>
        </div>
        <div className="toast-body">
          {message}
        </div>
      </div>
    </div>
  );
};

export default ToastNotification;
