import React, { useState } from 'react';
import CalculatorForm from './CalculatorForm';

function App() {
  const [result, setResult] = useState<number | null>(null);

  const handleResult = (value: number) => {
    setResult(value);
  };

  return (
    <div className="App">
      <h1>Calculator App</h1>
      <CalculatorForm onResult={handleResult} />
      {result !== null && <p>Result: {result}</p>}
    </div>
  );
}

export default App;
