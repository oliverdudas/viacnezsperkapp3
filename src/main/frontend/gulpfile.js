// Include gulp
var gulp = require('gulp');

// Include Our Plugins
var jshint = require('gulp-jshint');
var less = require('gulp-less');
var minifyCSS = require('gulp-minify-css');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');

//var refresh = require('gulp-livereload');
//var lr = require('tiny-lr');
//var server = lr();

var allJsFiles = 'src/app/*.js';
var allLessFiles = 'src/less/*.less';

// Lint Task
gulp.task('lint', function () {
    return gulp.src(allJsFiles)
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

gulp.task('less', function () {
    return gulp.src(allLessFiles)
        .pipe(less())
        .pipe(minifyCSS())
        .pipe(gulp.dest('dist'));
});

// Concatenate & Minify JS
gulp.task('scripts', function () {
    return gulp.src(allJsFiles)
        .pipe(concat('all.js'))
        .pipe(gulp.dest('dist'))
        .pipe(rename('all.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('dist'));
});

// Watch Files For Changes
gulp.task('watch', function () {
    gulp.watch(allJsFiles, ['lint', 'scripts']);
    gulp.watch(allLessFiles, ['less']);
});

// Default Task
gulp.task('build-release', ['lint', 'scripts', 'less']);
//gulp.task('default', ['lr-server', 'lint', 'scripts', 'less', 'watch']);
gulp.task('default', ['lint', 'scripts', 'less']);

