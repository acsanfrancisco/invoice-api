INSERT INTO tb_message_template (id, message, message_type)
VALUES (
    gen_random_uuid(),
    $$Olá, {{fullName}} !

Esta é uma mensagem automática do setor financeiro.
Informamos que uma nova fatura foi emitida no seu cadastro, documento: {{document}}.

Valor: {{grossValue}}
Desconto para bom pagador: {{discount}}
Data de Vencimento: {{dueDate}}

Garanta o valor especial de {{netValue}}, pagando até a data de vencimento !
Em caso de dúvidas, entre em contato.$$,
'INVOICE_ISSUED'
),
(
    gen_random_uuid(),
    $$Olá, {{fullName}} !

Esta é uma mensagem automática do setor financeiro.
Gostariamos de lembrar que sua fatura vence hoje, {{dueDate}}.

Valor: {{grossValue}}
Desconto para bom pagador: {{discount}}

Garanta o valor especial de {{netValue}} para pagamentos feitos até a data de vencimento.

Caso o pagamento ja tenha sido realizado, solicitamos, por gentileza, o comprovante de pagamento.
Em caso de dúvidas, estamos a disposição !$$,
'INVOICE_DUE_TODAY'
),
(
    gen_random_uuid(),
    $$Olá, {{fullName}} !

Esta é uma mensagem automática do setor financeiro.
Identificamos que sua fatura no valor de {{grossValue}}, com data de vencimento {{dueDate}}, encontra-se em atraso.
Pedimos, por gentileza, que verifique a situação do pagamento.

Caso o pagamento do valor de {{grossValue}} ja tenha sido realizado, solicitamos, por gentileza, o comprovante de pagamento.
Em caso de dúvidas, estamos a disposição !$$,
'INVOICE_OVERDUE'
),
(
    gen_random_uuid(),
    $$Olá, {{fullName}} !
Esta é uma mensagem automática do setor financeiro.
Identificamos que sua fatura, no valor de {{grossValue}}, encontra-se *PARCIALMENTE PAGA*.

Valor total pendente de pagamento: {{yetToPay}}

Caso o pagamento do valor restante de {{yetToPay}} já tenha sido realizado, solicitamos, por gentileza, o comprovante de pagamento.
Em caso de dúvidas, estamos a disposição !$$,
'INVOICE_PARTIALLY_PAID');