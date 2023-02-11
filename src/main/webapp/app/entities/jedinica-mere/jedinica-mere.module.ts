import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JedinicaMereComponent } from './list/jedinica-mere.component';
import { JedinicaMereDetailComponent } from './detail/jedinica-mere-detail.component';
import { JedinicaMereUpdateComponent } from './update/jedinica-mere-update.component';
import { JedinicaMereDeleteDialogComponent } from './delete/jedinica-mere-delete-dialog.component';
import { JedinicaMereRoutingModule } from './route/jedinica-mere-routing.module';

@NgModule({
  imports: [SharedModule, JedinicaMereRoutingModule],
  declarations: [JedinicaMereComponent, JedinicaMereDetailComponent, JedinicaMereUpdateComponent, JedinicaMereDeleteDialogComponent],
})
export class JedinicaMereModule {}
