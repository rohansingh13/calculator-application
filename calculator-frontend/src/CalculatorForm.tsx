import React, { useState } from 'react';
import axios from 'axios';

interface CalculatorFormProps {
  onResult: (result: number) => void;
}

const CalculatorForm: React.FC<CalculatorFormProps> = ({ onResult }) => {
  const [number1, setNumber1] = useState<number>(0);
  const [number2, setNumber2] = useState<number>(0);
  const [operation, setOperation] = useState<string>('add'); 
  const [result, setResult] = useState<number | null>(null);
  const [errorMessage, setError] = useState<string | null>(null);

  const handleSubmit = async () => {
    try {
      const response = await axios.post(        
        'https://owqc808789.execute-api.eu-north-1.amazonaws.com/test/calculator',
        { number1, number2, operation },
        { withCredentials: false }
      );    

      const { statusCode } = response.data;     

      if (!response.data) {        
        setError('An unexpected error occurred.');
        setResult(null);
        return;
      }    

      if (statusCode === 200) {
        setResult(response.data.body.result);
        setError(null);
      } else if ( statusCode === 400){
        if (response.data.error.includes('Division by zero is not allowed')) {
          setError('Division by zero is not allowed. Please enter a non-zero divisor.');          
        }else{
          setError(response.data.error);
          setResult(null);
        }        
      } else {
          setResult(null);
          setError(response.data.error || 'An unknown error occurred.');
        } 
    } catch (error:any) {     
      setError('An unexpected error occurred.');
      setResult(null);
    }
  };

  return (
    <div>
      <input
        type="number"
        value={number1}
        onChange={(e) => setNumber1(Number(e.target.value))}
      />
      <input
        type="number"
        value={number2}
        onChange={(e) => setNumber2(Number(e.target.value))}
      />
      <select value={operation} onChange={(e) => setOperation(e.target.value)}>
        <option value="add">Add</option>
        <option value="subtract">Subtract</option>
        <option value="multiply">Multiply</option>
        <option value="divide">Divide</option>
      </select>
      <button onClick={handleSubmit}>Submit</button>
      <br />
      {result !== null && (
        <div>
          Result: {result}
        </div>
      )}
      {errorMessage && (
        <div style={{ color: 'red' }}>
          Error: {errorMessage}
        </div>
      )}
    </div>
  );
};

export default CalculatorForm;
