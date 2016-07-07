package by.epam.news.service.impl;

import by.epam.news.dao.*;
import by.epam.news.domain.*;
import by.epam.news.service.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class NewsServiceFacadeImpl implements NewsServiceFacade {
    private NewsService newsService;
    private CommentService commentService;
    private TagService tagService;
    private AuthorService authorService;

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public Long saveNews(NewsDTO newsDTO) throws ServiceException {
        Long newsId = newsDTO.getNews().getId();
        if (newsId == null) {
            newsId = newsService.addNews(newsDTO.getNews());
        } else {
            newsService.updateNews(newsDTO.getNews());
            authorService.unlinkAllAuthorsFromNews(newsId);
            tagService.unlinkAllTagsFromNews(newsId);
        }
        authorService.linkAuthorToNews(newsId, newsDTO.getAuthor().getId());
        List<Tag> tags = newsDTO.getTags();
        if (tags != null) {
            for (Tag tagId : tags) {
                tagService.linkTagToNews(newsId, tagId.getId());
            }
        }
        return newsId;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public NewsDTO findById(Long newsId) throws ServiceException {
        NewsDTO newsDTO = null;
        News news = newsService.findNewsById(newsId);
        if (news != null) {
            Author author = authorService.findNewsAuthors(newsId).get(0);
            List<Comment> comments = commentService.findCommentForNews(newsId);
            List<Tag> tags = tagService.findNewsTags(newsId);
            newsDTO = new NewsDTO(news, author, comments, tags);
        }
        return newsDTO;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public List<NewsDTO> findAllNews(Long page, Long limit) throws ServiceException {
        List<NewsDTO> newsDTOList;
        List<News> news = newsService.findAllNews(page, limit);
        if (news.size() > 0) {
            newsDTOList = createNewsDTOList(news);
        } else {
            newsDTOList = null;
        }
        return newsDTOList;
    }

    //TODO check whether necessary
    /*
    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public List<NewsDTO> findNewsByAuthor(Long authorId) throws ServiceException {
        List<NewsDTO> newsDTOList;
        try {
            List<News> news = newsDao.selectNewsByAuthor(authorId);
            if (news.size() > 0) {
                newsDTOList = createNewsDTOList(news);
            } else {
                newsDTOList = null;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDTOList;
    }*/

    @Override
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    public void deleteNews(Long newsId) throws ServiceException {
        commentService.deleteCommentsForNews(newsId);
        newsService.deleteNews(newsId);
    }

    @Override
    public List<NewsDTO> findBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException {
        List<NewsDTO> newsDTOList = null;
        List<News> news = newsService.findNewsBySearchCriteria(searchCriteria);
        if (news.size() > 0) {
            newsDTOList = createNewsDTOList(news);
        }
        return newsDTOList;
    }

    private List<NewsDTO> createNewsDTOList(List<News> news) throws ServiceException {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for (News newsMessage: news) {
            long newsId = newsMessage.getId();
            Author author = authorService.findNewsAuthors(newsId).get(0);
            List<Tag> tags = tagService.findNewsTags(newsId);
            NewsDTO newsDTO = new NewsDTO(newsMessage, author, tags);
            newsDTO.setCommentCount(commentService.countNewsComments(newsId));
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }
}
