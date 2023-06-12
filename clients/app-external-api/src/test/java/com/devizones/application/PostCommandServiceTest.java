package com.devizones.application;

import com.devizones.ExternalApiApplication;
import com.devizones.application.post.service.PostCommandService;
import com.devizones.post.rds.entity.PostEntity;
import com.devizones.post.rds.repository.PostLikeRepository;
import com.devizones.post.rds.repository.PostQueryRepository;
import com.devizones.post.rds.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostCommandServiceTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCommandService postCommandService;
    @Autowired
    private PostQueryRepository postQueryRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;

    @DisplayName("댓글 작성 시 여러개 요청이 동시에 발생할 경우 댓글수가 맞는지 확인합니다.")
    @Test
    void like() throws InterruptedException {
        // given
        final Integer requestCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(requestCount);


        PostEntity postEntity = PostEntity.builder()
                                          .id(null)
                                          .writerId(1L)
                                          .title("title")
                                          .contents("contents")
                                          .summary("summary")
                                          .visible(true)
                                          .deleted(false)
                                          .likeCount(0L)
                                          .build();


        PostEntity savedPostEntity = postRepository.save(postEntity);


        // when
        for (int i = 0; i < requestCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    postCommandService.like(savedPostEntity.getId(), (long) (finalI + 1));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        PostEntity findPostEntity = postQueryRepository.findById(savedPostEntity.getId())
                                                       .orElseThrow();
        assertThat(findPostEntity.getLikeCount()).isEqualTo((long) requestCount);
        assertThat(postLikeRepository.count()).isEqualTo((long) requestCount);
    }

    @DisplayName("댓글 작성 시 여러개 요청이 동시에 발생할 경우 댓글수가 맞는지 확인합니다.")
    @Test
    void unLike() throws InterruptedException {
        // given
        final Integer requestCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch1 = new CountDownLatch(requestCount);


        PostEntity postEntity = PostEntity.builder()
                                          .id(null)
                                          .writerId(1L)
                                          .title("title")
                                          .contents("contents")
                                          .summary("summary")
                                          .visible(true)
                                          .deleted(false)
                                          .likeCount(0L)
                                          .build();


        PostEntity savedPostEntity = postRepository.save(postEntity);
        for (int i = 0; i < requestCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    postCommandService.like(savedPostEntity.getId(), (long) (finalI + 1));
                } finally {
                    latch1.countDown();
                }
            });
        }
        latch1.await();

        PostEntity findPostEntity1 = postQueryRepository.findById(savedPostEntity.getId())
                                                       .orElseThrow();
        assertThat(findPostEntity1.getLikeCount()).isEqualTo((long) requestCount);

        CountDownLatch latch2 = new CountDownLatch(requestCount);

        // when
        for (int i = 0; i < requestCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    postCommandService.unLike(savedPostEntity.getId(), (long) (finalI + 1));
                } finally {
                    latch2.countDown();
                }
            });
        }
        latch2.await();

        // then
        PostEntity findPostEntity = postQueryRepository.findById(savedPostEntity.getId())
                                                       .orElseThrow();
        assertThat(findPostEntity.getLikeCount()).isEqualTo(0L);
        assertThat(postLikeRepository.count()).isEqualTo(0L);
    }
}
