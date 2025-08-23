create table if not exists quizzes(
    id UUID primary key,
    topic text not null
);

create table if not exists questions(
    id UUID primary key,
    question_value text not null,
    quiz_id UUID not null,
    constraint quiz_fk
        foreign key (quiz_id)
        references quizzes (id)
);

create table if not exists answers(
    id UUID primary key,
    answer_value text not null,
    is_correct_answer boolean not null default false,
    question_id UUID not null,
    constraint question_fk
        foreign key (question_id)
        references questions (id)
);
