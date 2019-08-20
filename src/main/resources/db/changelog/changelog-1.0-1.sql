INSERT INTO public.uva_loan
(loan_date, loan_dni_holder, loan_dni_coholder, pesos_value, uva_value)
VALUES('2017-09-20', 11111111, 22222222, 1132800.0, 56470.59);

INSERT INTO public.uva_loan_fee
(loan_id, fee_number, fee_date, initial_capital, initial_interest, initial_total)
VALUES(1, 1, '2017-11-10', 3265.78, 5539.86, 8805.64);

INSERT INTO public.uva_exchange
(exchange_day, rate)
VALUES('2017-11-10', 20.62);

