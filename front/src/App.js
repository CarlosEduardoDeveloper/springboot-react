import { useEffect, useState } from 'react';
import './App.css';
import Formulario from './Formulario';
import Tabela from './Tabela';

function App() {

  const produto = {
    id: 0,
    nome: '',
    marca: ''
  }

  //useState
const [btnCadastrar, setBtnCadastrar] = useState(true);
const [produtos, setProdutos] = useState([]);
const [objProduto, setObjProduto] = useState(produto);

  //useEffect
  useEffect(()=>{
    fetch("http://localhost:8080/listar")
    .then(retorno => retorno.json())
    .then(retorno_convertido => setProdutos(retorno_convertido));
  }, []);

  //Obtendo dados do form
const aoDigitar = (e) => {
  setObjProduto({...objProduto, [e.target.name]:e.target.value});
}

//cadastrar produtos
const cadastrar = () => {
  fetch('http://localhost:8080/cadastrar', {
    method:'post',
    body:JSON.stringify(objProduto),
    headers:{
      'Content-type':'application/json',
      'Accept':'application/json'
    }
  })
  .then(retorno => retorno.json())
  .then(retorno_convertido => {
    if(retorno_convertido.mensagem !== undefined){
      alert(retorno_convertido.mensagem);
    }else{
      setProdutos([...produtos, retorno_convertido]);
      alert('Produto cadastrado com sucesso!');
      limparForm();
    }
  });
}

//limpar form
const limparForm = () => {
  setObjProduto(produto);
  setBtnCadastrar(true);
}

//selecionar protudo
const selecionarProduto = (indice) => {
  setObjProduto(produtos[indice]);
  setBtnCadastrar(false);
}

//remover produtos
const remover = () => {
  fetch('http://localhost:8080/remover/'+objProduto.id, {
    method:'delete',
    headers:{
      'Content-type':'application/json',
      'Accept':'application/json'
    }
  })
  .then(retorno => retorno.json())
  .then(retorno_convertido => {
      alert(retorno_convertido.mensagem);
      let vetorTemp = [...produtos];
      let indice = vetorTemp.findIndex((p) => {
        return p.id === objProduto.id;
      });
      vetorTemp.splice(indice, 1);

      setProdutos(vetorTemp);

      limparForm();

  });
}

//alterar produtos
const alterar = () => {
  fetch('http://localhost:8080/alterar', {
    method:'put',
    body:JSON.stringify(objProduto),
    headers:{
      'Content-type':'application/json',
      'Accept':'application/json'
    }
  })
  .then(retorno => retorno.json())
  .then(retorno_convertido => {
    if(retorno_convertido.mensagem !== undefined){
      alert(retorno_convertido.mensagem);
    }else{
      alert('Produto alterado com sucesso!');
      let vetorTemp = [...produtos];
      let indice = vetorTemp.findIndex((p) => {
        return p.id === objProduto.id;
      });
      vetorTemp[indice] = objProduto;

      setProdutos(vetorTemp);

      limparForm();
    }
  });
}




  return (
    <div>
      <Formulario botao={btnCadastrar} eventoTeclado={aoDigitar} cadastrar={cadastrar} obj={objProduto} cancelar={limparForm} remover={remover}
        alterar={alterar}/>
      <Tabela vetor={produtos} selecionar={selecionarProduto}/>
    </div>
  );
}

export default App;
