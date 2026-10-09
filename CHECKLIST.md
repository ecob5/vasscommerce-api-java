# Checklist - IoC/DI e separação entre Controller e Model

- [x] Controladores não fazem `new` de implementações; dependem de interfaces.
- [x] Há duas implementações `@Service` de `ProdutoModel` (`produtoModelMemoria` e `produtoModelSql`).
- [x] A seleção da implementação é feita por `@Qualifier` no `ProdutoController`.
- [x] A injeção é feita por construtor; não há `@Autowired` em campos de produção.
- [x] `ProdutoController` retorna `201 Created`, `200 OK` e `404 Not Found` corretamente.
- [x] A validação de `ProdutoCreateRequest` continua ativa via `@Valid`.
- [x] Erros de validação e regras de entrada retornam JSON padronizado via `@RestControllerAdvice`.

## Troca da implementação

O controller usa a implementação em memória por padrão. Para demonstrar a implementação SQL
simulada, troque o valor do `@Qualifier` para `produtoModelSql`. As duas implementações são
beans independentes e a segunda delega ao armazenamento em memória enquanto não há banco configurado.
