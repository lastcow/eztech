package me.chen.eztech.service;


import me.chen.eztech.dto.QuestionDto;
import me.chen.eztech.model.Question;
import me.chen.eztech.model.User;
import me.chen.eztech.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserService userService;

    public Question newQuestionByDto(QuestionDto questionDto){
        // Get user
        Optional<User> userOptional = userService.getUserByUsername(questionDto.getUsername());
        if(userOptional.isPresent()){

            Question question = new Question();
            question.setOwner(userOptional.get());
            question.setQuestion(questionDto.getQuestion());
            question.setCreatedat(new Timestamp(System.currentTimeMillis()));

            return questionRepository.save(question);
        }

        return null;

    }
}
