
```
@Test  
@DisplayName("게시글 전체 조회")  
void getAll() {  
    // given  
    List<Article> articles = new ArrayList<>();  
    AddArticleRequest addArticleRequest = new AddArticleRequest("test","testContent");  
    Article testArticle = addArticleRequest.toEntity();  
    articles.add(testArticle);  
    given(todoRepository.findAll()).willReturn(articles);  
    // when  
    List<Article> foundArticles = todoService.findAll();  
    // then  
    assertNotNull(foundArticles);  
    assertFalse(foundArticles.isEmpty());  
    assertEquals(1, foundArticles.size());  
    assertEquals(testArticle.getId(), foundArticles.get(0).getId());  
    assertEquals(testArticle.getTitle(), foundArticles.get(0).getTitle());  
    assertEquals(testArticle.getContent(), foundArticles.get(0).getContent());  
}
```
* given-when-then 사용 했습니다.
* AddArticleRequest DTO를 사용했습니다.
* List<Article> foundArticles 0번째와 비교해서 검증했습니다.
