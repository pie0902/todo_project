
#### 죄송합니다 아직 테스트 코드 구현이 어려워서 서비스만 2개 진행된 상태로 제출 합니다 ㅠㅠ ####

### 게시글 전체 조회
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

### 게시글 한개씩 조회

```
@Test  
@DisplayName("게시글 한개씩 조회")  
void getId(){  
    //given  
    AddArticleRequest addArticleRequest = new AddArticleRequest("test","testContent");  
    Article testArticle = addArticleRequest.toEntity();  
    testArticle.setId(1L);  
    given(todoRepository.findById(testArticle.getId())).willReturn(Optional.of(testArticle));  
    //when  
    Article foundArticle = todoService.findById(testArticle.getId());  
    //then  
    assertNotNull(foundArticle);  
    assertEquals(testArticle.getId(), foundArticle.getId());  
    assertEquals(testArticle.getTitle(), foundArticle.getTitle());  
    assertEquals(testArticle.getContent(), foundArticle.getContent());  
}
```

* 전체조회와 비슷하지만 todoServic의 findById를 사용해서 검증 했습니다.
