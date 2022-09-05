import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {TesteroAPI as api} from "../components/TesteroAPI";
import chevron_icon from "../assets/right-chevron.png";



function ExamList(){
  let navigate = useNavigate();
  const [examList, setExamList] = useState(null);

  useEffect(() => {
    api.allTests().then((data) => (setExamList(data))).catch((error) => (alert("Errore durante il caricamento dei test.")));
  }, []);

  function zeroPad(num){
    return num.toString().padStart(2,'0');
  }

  function formatDate(date){
    var d  = new Date(date);
    var date_string = zeroPad(d.getDate()) + '-'+ zeroPad(d.getMonth()+1) +'-'+ d.getFullYear();
    var time_string = zeroPad(d.getHours()) + ':'+ zeroPad(d.getMinutes());
    return date_string + ' ' + time_string;
  }

  return (
      <section className='page-centered-container'>
        <h1 tabIndex="0">Test disponibili</h1>

        <div>
          {examList? (
            examList.allTests.map((elem) => {
              return (
                <div className='page-container-row testList-row' key={elem.id}>
                    <div className='page-testlist-row-container'>
                      <div className='page-testlist-row-info'>
                        <div tabIndex="0" className='page-testlist-row-data'>{formatDate(elem.data)}</div>
                        <div tabIndex="0" className='page-testlist-row-data'>{elem.nome}</div>
                      </div>
                      <div className='page-testlist-row-actions btn-bar' id={`btn-bar${elem.id}`}>
                        <button onClick={() => {
                          navigate(`/${elem.id}/question/0`);
                        }}>Avvia esame</button>
                      </div>
                    </div>
                </div>
              )
            })
          ) : (
            <h1> Caricando </h1>
            )}
        </div>
      </section>


  );
}

export {ExamList}