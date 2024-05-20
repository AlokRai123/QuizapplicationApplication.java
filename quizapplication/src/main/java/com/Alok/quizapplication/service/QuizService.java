package com.Alok.quizapplication.service;

import com.Alok.quizapplication.Dao.QuestionDao;
import com.Alok.quizapplication.Dao.QuizDao;
import com.Alok.quizapplication.model.Question;
import com.Alok.quizapplication.model.QuestionWrapper;
import com.Alok.quizapplication.model.Quiz;
import com.Alok.quizapplication.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> question = questionDao.findRandomQuestionByCategory(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(question);
        quizDao.save(quiz);
        return new ResponseEntity<>("success",HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionFromDB = quiz.get().getQuestion();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for(Question q : questionFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTittle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> question = quiz.getQuestion();
        int right =0;
        int i =0;
        for(Response responses : response){
            if(responses.getResponse().equals(question.get(i).getRightAnswer()))
             right++;

            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
