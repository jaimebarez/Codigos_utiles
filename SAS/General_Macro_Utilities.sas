/**
 *	\file			General_Macro_Utilities.sas
 *	\brief			Defines some macro utilities that are useful for other sas codes to import
 * 	
 * 
 *	\authors		Jaime Bárez Lobato	
 *
 *	20160610
 **/


/*************************************************************	
 *	Converts date from one format to another
 *	Example: %let dt=%convertDate(2005-04-03, YYMMDD10., YYMMN6.)->200504;
 *	More info of date formats: https://support.sas.com/documentation/cdl/en/lrcon/62955/HTML/default/viewer.htm#a002200738.htm
 */
%macro convertDate(date_, format_from_, format_to_);
	%local val_num_aux;
	%let val_num_aux = %sysfunc(inputn(&date_., &format_from_.));

	%sysfunc(putn(&val_num_aux.,&format_to_.))
%mend convertDate;

/*************************************************************	
 *	Returns a boolean, telling when the parameter is blank or not
 *	Examples:	%isBlank()			-returns->1
 *				%isBlank( )			-returns->1
 *				%isBlank(%str( ))	-returns->1
 *				%isBlank(hello)		-returns->0
 */
%macro isBlank(param_);
	%sysevalf(%superq(param_)=,boolean)
%mend isBlank;

/*************************************************************
 *	Gets the nth item of a list.
 *	Example: 
 *	%let myList=hello 4 6 8 10 12 14 16;
 *	%item(&myList., 5)	-returns->10
 *	%item(&myList., 1)	-returns->hello
 */
%macro item(list_, n_);
	%local val;
	%let val=%scan(%trim(%left(&list_.)), &n_., %str( ));
	&val.
%mend item;

/*************************************************************	
 *	Counts the number of items in a list
 *	Example:
 *	%let myList=hello 4 6 8 10 12 14 16;
 *	%count(&myList.)-returns->8
 */
%macro count(list_);
	%local m_list count;
	%let m_list=%trim(%left(&list_.));

	%if(%isBlank(&m_list.)) %then 	%let count=0;
	%else 							%let count=%sysfunc(countw(&m_list.,,s));
	&count.
%mend count;

/*************************************************************
 *	Delimits a list of items separated by an space with:
 *	A separator, a prefix and a suffix for each element
 *	Examples:
 *	%let mylist=2 4 6;
 *	%delimit_list(&mylist.,%str(?),a,z)		-returns->a2z?a4z?a6z
 *	%delimit_list(&mylist.,%str(_),bla,)	-returns->bla2_bla4_bla6
 */
%macro delimit_list(list_, separator_, prefix_, suffix_);
	%local count i delim_list;
	%let delim_list=;
	%let count = %count(&list_.);

	%do i = 1 %to &count.;
		%local next;
		%let next = %item(&list_., &i.);
		%let delim_list = &delim_list.&prefix_.&next.&suffix_.;

		%if(&i. ne &count.) %then
			%let delim_list = &delim_list.&separator_.;
	%end;

	&delim_list.
%mend delimit_list;

/*************************************************************
 *	Delimits a list of items separated by an space with:
 *	A comma separator, and a prefix for each element
 *	Example:
 *	%let mylist=2 4 6;
 *	%delimit_list_comma(&mylist.,pre)		-returns->pre2,pre4,pre6
 */
%macro delimit_list_comma(list_, prefix_);
	%delimit_list(&list_., %str(,),&prefix_., %str())
%mend delimit_list_comma;

/*************************************************************
 *	Returns the last day of a month.
 *	Receives a date with an specific format, and returns the last day of that
 *	date's month and year.
 */
%macro getLastDayMonth(date_in_, in_format_, out_format_);
	%sysfunc(intnx(day,%sysfunc(intnx(month,%sysfunc(inputn(&date_in_.,&in_format_.)) ,1)),-1),&out_format_.)
%mend getLastDayMonth;

/*************************************************************
 *	Returns the first day of a month.
 *	Receives a date with an specific format, and returns the first day of that
 *	date's month and year.
 */
%macro getFirstDayMonth(date_in_, in_format_, out_format_);
	%sysfunc(intnx(month,%sysfunc(intnx(month,%sysfunc(inputn(&date_in_.,&in_format_.)) ,1)),-1),&out_format_.)
%mend getFirstDayMonth;

/*************************************************************
 *	Sums a a given interval to a date. (If a negative number is provided, the function subtracts).
 *	Example	%sumDate(201512, month, -1, yymmn6., yymmn6.) -returns->201511
 */
%macro sumDate(date_, interval_, increment_, in_format_, out_format_);
	%sysfunc(intnx(&interval_.,%sysfunc(inputn(&date_.,&in_format_.)),&increment_.,same),&out_format_.)
%mend sumDate;

/*************************************************************
 *	Sums X days to a date. (If a negative number is provided, the function subtracts).
 */
%macro sumDay(date_, increment_, in_format_, out_format);
	%sumDate(&date_., day, &increment_., &in_format_., &out_format.)
%mend;

/*************************************************************
 *	Sums X months to a date. (If a negative number is provided, the function subtracts).
 */
%macro sumMonth(date_, increment_, in_format_, out_format);
	%sumDate(&date_., month, &increment_., &in_format_., &out_format.)
%mend;

/*************************************************************
 *	Sums X years to a date. (If a negative number is provided, the function subtracts).
 */
%macro sumYear(date_, increment_, in_format_, out_format);
	%sumDate(&date_., year, &increment_., &in_format_., &out_format.)
%mend;

/*************************************************************
 *	Sleeps for X seconds
 */
%macro Sleep(seconds_);
	%local sleep;
	%let sleep	=%sysfunc(SLEEP(&seconds_.000));

	/*	   DATA _NULL_; */
	/*	        vVar1=SLEEP(&pSeconds_.); */
	/*	   RUN; */
%mend Sleep;

/*************************************************************
 *	Returns current time formatted
 */
%macro getTime_formatted(format_);
	%sysfunc(datetime(), &format_.)
%mend;

/*************************************************************
 *	Returns current time
 */
%macro getTime();
	%getTime_formatted(datetime19.)
%mend;
 
/*************************************************************
 *	Deletes all files from a library
 */
%macro deleteAllFilesFromLibrary(lib_name_);

	proc datasets library=&lib_name_. kill nolist;
		run;
	quit;

%mend deleteAllFilesFromLibrary;

/*************************************************************
 *	Deletes all files from WORK library
 */
%macro deleteAllFilesFromWORK();
	%deleteAllFilesFromLibrary(WORK);
%mend deleteAllFilesFromWORK;

/*************************************************************
 *	Creates a directory (linux server)
 */
%macro createDirectory(dir_);

	data _null_;
		x "mkdir '&dir_.'";
	run;

%mend createDirectory;

/*************************************************************
 *	Removes a file (linux server)
 */
%macro removeFile(file_);

	data _null_;
		x "rm '&file_.'";
	run;

%mend removeFile;

/*************************************************************
 *	Copies a file (linux server)
 */
%macro copyFile(file_from_, file_to_);
	%put =======================>"cp '&file_from_.' '&file_to_.'";
		data _null_;
			x "cp '&file_from_.' '&file_to_.'";
		run;
%mend copyFile;


/*************************************************************
 *	Like %index(..,..) function, but searchs for the last occurrence instead the first one
 */
%macro index_last(source_, excerpt_);
	%local index;
	%let index			=0;
	%let source_reverse	=%sysfunc(reverse(&source_.));
	%let index			=%index(&source_reverse.,&excerpt_.);

	%if(index ne 0) %then
		%let index=%eval(%length(&source_reverse.) - &index. + 1);
	&index.
%mend index_last;

/*************************************************************
 *	Returns the difference between two datetime19. datetimes.
 *	If difference is higher tan a day, there will be more than 24 hours
 *	in the output.
 *  format_->1 :Duration formated (TIME20.). format_->0: Number of seconds
 */
%macro hms_between_datetimes(dt1_, dt2_, format_);
	%local t;
	%let t	= %sysfunc(intck(SECOND, "&dt1_."dt, "&dt2_."dt));

	%if(&format_.=1) %then
		%do;
			%let t=%sysfunc(inputn(&t., SECOND), TIME20.);
		%end;

	&t.
%mend hms_between_datetimes;

/*************************************************************
 *	Unlinks(clears) all the libraries
 */
%macro desvincular_librerias();
	libname _all_ clear;
%mend desvincular_librerias;

/*************************************************************
 *	Shows a Log with a Severity
 **/
%macro log(severity_, message_);
	%put &severity_.: (%getTime()): %str(&message_.);
%mend log;

/*************************************************************
 *	Shows a Log with a Severity INFO
 **/
%macro log_info(message_);
	%log(INFO, %str(&message_.));
%mend log_info;

/*************************************************************
 *	Shows a Log with a Severity DEBUG
 **/
%macro log_debug(message_);
	%log(DEBUG, %str(&message_.));
%mend log_debug;

/*************************************************************
 *	Shows a Log with a Severity WARNING
 **/
%macro log_warn(message_);
	%log(WARNING, %str(&message_.));
%mend log_warn;
 
/*************************************************************
 *	Shows a Log with a Severity ERROR
 **/
%macro log_error(message_);
	%log(ERROR, %str(&message_.));
%mend log_error;

%macro nombre_columna_valida_sas(nombre_columna);
TRANWRD(
  TRANWRD(
   TRANWRD(
    TRANWRD(
     TRANWRD(
      TRANWRD(
       TRANWRD(
        TRANWRD(
         TRANWRD(
          TRANWRD(
           TRANWRD(
            TRANWRD(
			 TRANWRD(
              TRANWRD(substr(strip(&nombre_columna.),1,min(32, length(strip(&nombre_columna.))))
                     ,'á','a')
                    ,'é','e')
                   ,'í','i')
                  ,'ó','o')
                 ,'ú','u')
                ,'Á','A')
               ,'É','E')
              ,'Í','I')
             ,'Ó','O')
            ,'Ú','U')
           ,' ','_')
          ,'ü','u')
         ,'Ü','U')
        ,'/','_')
%mend nombre_columna_valida_sas;
 
/*************************************************************
 *	
 */
%macro get_nombre_columna_valida_sas(nombre_columna, nombre_columna_valida_out);
	data _NULL_;
		call symput("&nombre_columna_valida_out.",%nombre_columna_valida_sas("&nombre_columna."));
	run;
%mend get_nombre_columna_valida_sas;

/*************************************************************
 *	Checks if a data is sorted (at least) by "vars_sorted_"-> Can be one variable, or more,
 *	separated by spaces
 */
%macro checkIfSorted(libname_, data_name_, vars_sorted_, sorted_output_);

	%let &sorted_output_.	=0;
		
	%local m_names m_sortedby;
	%let m_names	=%str();
	%let m_sortedby	=%str();

	proc sql noprint;
		select strip(name), sortedby into :m_names separated by " ", :m_sortedby separated by " "
		from (select name, sortedby 
				from sashelp.vcolumn 
				where 		upcase(libname) = upcase("&libname_.") 
						and upcase(memname) = upcase("&data_name_.")
						and not missing(sortedby) and sortedby ne 0
			)
		order by sortedby;
	quit;

	%if(%count(&m_names.) < %count(&vars_sorted_.)) %then %do;
		%let &sorted_output_. =0;
	%end;
	%else %do;
		%local i;
		%let &sorted_output_. =1;
		%do i=1 %to %count(&vars_sorted_.);
			%if( 	(&sorted_output_.						ne	0)									and
					(%item(&m_sortedby., &i.) 				=	&i.) 								and 
					(%upcase(%item(&vars_sorted_., &i.))	=	%upcase(%item(&m_names., &i.)))
				) %then %do;
				%let &sorted_output_. =1;
			%end;
			%else %do;
				%let &sorted_output_. =0;
			%end;
		%end;
	%end;

%mend checkIfSorted;

/*************************************************************
 *	Checks if a data is at least ordered by "var_ordered_"-> can be one variable, or more,
 *	separated spaces.
 */
%macro checkIfOrdered(libname_, data_name_, var_ordered_, ordered_output_);

	%local m_sorted;

	%checkIfSorted(&libname_., &data_name_., &var_ordered_.,m_sorted);
	%if(&m_sorted. ^=1) %then %do;
		%local m_null_log;
		%if(&SYSSCP.=WIN) %then	%let m_null_log="NULL";
		%else 					%let m_null_log="/dev/null";

		proc printto log=&m_null_log.;run;

		%local m_syserr m_syserr_text;
		data _NULL_;
		    set &libname_..&data_name_.(keep = &var_ordered_.);
		    by &var_ordered_.;
		run;
		%let m_syserr		=&SYSERR.;
		%let m_syserr_text	=&SYSERRORTEXT.;

		proc printto;run;

		%local m_notsorted_syserr;
		%let m_notsorted_syserr	=1012;

		%if(&m_syserr. = &m_notsorted_syserr. and %sysfunc(exist(&libname_..&data_name_.)))	%then %do;
			%let m_sorted =0;
		%end;
		%else %if (&m_syserr. = 0)	%then %do;
			%let m_sorted =1;
		%end;
		%else %do;
			%let m_sorted =0;
			%put ERROR:  &SYSERRORTEXT..;
		%end;
	%end;
	
	%let &ordered_output_.=&m_sorted.;

%mend checkIfOrdered;

/*************************************************************
 *	Given a number, returns A-Z values. (Can be more than one letter)
 *	This is useful, for example, when doing lots of left joins in an sql, to assign
 *	an ID to each of the crossed tables .
 *	Example:	1->A, 2->B, 26->Z, 27->AA, 28->AB, etc
 **/
%macro num_to_A_Z_id(num_);
	%local auxiliar continuar id rank_A rank_Z num_alphabet_letters;
	%let auxiliar	=%eval(&num_.-1);
	%let continuar	=1;
	%let id			=%str();
	%let rank_A		=%sysfunc(RANK(A));
	%let rank_Z		=%sysfunc(RANK(Z));
	%let num_alphabet_letters	= %eval(&rank_Z. - &rank_A. + 1);
	
	%do %while (&continuar. = 1);
		%let division		=%sysfunc(int(%eval(&auxiliar. / &num_alphabet_letters.)));
		%let resto 			=%sysfunc(mod(&auxiliar., &num_alphabet_letters.));
		%if(&division. < &num_alphabet_letters.) %then %do;	
			%let id			=%sysfunc(BYTE(%eval(&resto. + &rank_A.)))&id.;
			%let continuar	=0;
		%end;
		%if(&division.>0) %then %do;
			%let id			=%sysfunc(BYTE(%eval(&division. - 1 + &rank_A.)))&id.;
		%end;
		%let auxiliar		=%eval(&auxiliar. - &num_alphabet_letters.);
	%end;
	&id.
%mend num_to_A_Z_id;

/*************************************************************
 *	Deletes a data if exists
 */
%macro delete_if_exists(data_);
	%if( %sysfunc(exist(&data_.))) %then %do;
		proc delete data=&data_.;run;
	%end;
%mend delete_if_exists;

/*************************************************************
 *	Returns a true value
 */
%macro is_syserr();
	%sysevalf(&SYSERR.>0,boolean)
%mend is_syserr;

/*************************************************************
 *	Aborts the program, showing a message
 */
%macro abort_message(message_);
	%log_error(&message_.);
	%abort;
%mend abort_message;

/*************************************************************
 *	Aborts if there just has been a SYSERR
 */
%macro abort_if_syserr(message_);
	%if(%is_syserr()) %then %do;
		%abort_message(&message_.);
	%end;
%mend abort_if_syserr;